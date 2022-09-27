import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * This is a basic set of unit tests for ParternMatching.
 *
 * Passing these tests doesn't guarantee any grade on these assignments. These
 * student JUnits that we provide should be thought of as a sanity check to
 * help you get started on the homework and writing JUnits in general.
 *
 * We highly encourage you to write your own set of JUnits for each homework
 * to cover edge cases you can think of for each data structure. Your code must
 * work correctly and efficiently in all cases, which is why it's important
 * to write comprehensive tests to cover as many cases as possible.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class PatternMatchingStudentTest {

    private static final int TIMEOUT = 200;

    private String kmpPattern;
    private String kmpText;
    private String kmpNoMatch;
    private List<Integer> kmpAnswer;
    private List<Integer> kmpPatternEquivalencyAnswer;

    private String sellPattern;
    private String sellText;
    private String sellNoMatch;
    private List<Integer> sellAnswer;

    private String multiplePattern;
    private String multipleText;
    private List<Integer> multipleAnswer;

    private List<Integer> emptyList;

    private CharacterComparator comparator;


    @Before
    public void setUp() {
        kmpPattern = "ababa";
        kmpText = "ababaaababa";
        kmpNoMatch = "ababbaba";

        kmpAnswer = new ArrayList<>();
        kmpAnswer.add(0);
        kmpAnswer.add(6);

        kmpPatternEquivalencyAnswer = new ArrayList<>();
        kmpPatternEquivalencyAnswer.add(0);

        sellPattern = "sell";
        sellText = "She sells seashells by the seashore.";
        sellNoMatch = "sea lions trains cardinal boardwalk";

        sellAnswer = new ArrayList<>();
        sellAnswer.add(4);

        multiplePattern = "ab";
        multipleText = "abab";

        multipleAnswer = new ArrayList<>();
        multipleAnswer.add(0);
        multipleAnswer.add(2);

        emptyList = new ArrayList<>();

        comparator = new CharacterComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testBuildFailureTable() {
        /*
            pattern: ababa
            failure table: [0, 0, 1, 2, 3]
            comparisons: 4
         */
        int[] failureTable = PatternMatching
                .buildFailureTable(kmpPattern, comparator);
        int[] expected = {0, 0, 1, 2, 3};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 4.", 4, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPMatch() {
        /*
            pattern: ababa
            text: ababaaababa
            indices: 0, 6
            expected total comparison: 18

            failure table: [0, 0, 1, 2, 3]
            comparisons: 4

        a | b | a | b | a | a | a | b | a | b | a
        --+---+---+---+---+---+---+---+---+---+---
        a | b | a | b | a |   |   |   |   |   |
        - | - | - | - | - |   |   |   |   |   |         comparisons: 5
          |   | a | b | a | b | a |   |   |   |
          |   |   |   |   | - |   |   |   |   |         comparisons: 1
          |   |   |   | a | b | a | b | a |   |
          |   |   |   |   | - |   |   |   |   |         comparisons: 1
          |   |   |   |   | a | b | a | b | a |
          |   |   |   |   | - | - |   |   |   |         comparisons: 2
          |   |   |   |   |   | a | b | a | b | a
          |   |   |   |   |   | - | - | - | - | -       comparisons: 5

         comparisons: 14
         */
        assertEquals(kmpAnswer,
                PatternMatching.kmp(kmpPattern, kmpText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 18.", 18, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPNoMatch() {
        /*
            pattern: ababa
            text: ababbaba
            indices: -
            expected total comparison: 10

            failure table: [0, 0, 1, 2, 3]
            comparisons: 4

        a | b | a | b | b | a | b | a
        --+---+---+---+---+---+---+---
        a | b | a | b | a |   |   |
        - | - | - | - | - |   |   |     comparisons: 5
          |   | a | b | a | b | a |
          |   |   |   | - |   |   |     comparisons: 1

        comparisons: 6
         */
        assertEquals(emptyList,
                PatternMatching.kmp(kmpPattern, kmpNoMatch, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 10.", 10, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPLongerText() {
        /*
            pattern: ababbaba
            text: ababa
            indices: -
            expected total comparison: 0
         */
        assertEquals(emptyList,
                PatternMatching.kmp(kmpNoMatch, kmpPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPPatternEqualsText() {
        /*
            pattern: ababa
            text: ababa
            indices: -
            expected total comparison: 5 or 9 (if failure table is built)
         */
        assertEquals(kmpPatternEquivalencyAnswer,
                PatternMatching.kmp(kmpPattern, kmpPattern, comparator));
        assertTrue("Comparison count is different than expected",
                comparator.getComparisonCount() == 5
                        || comparator.getComparisonCount() == 9);
    }

    @Test(timeout = TIMEOUT)
    public void testBuildLastTable() {
        /*
            pattern: sell
            last table: {s : 0, e : 1, l : 3}
         */
        Map<Character, Integer> lastTable = PatternMatching
                .buildLastTable(sellPattern);
        Map<Character, Integer> expectedLastTable = new HashMap<>();
        expectedLastTable.put('s', 0);
        expectedLastTable.put('e', 1);
        expectedLastTable.put('l', 3);
        assertEquals(expectedLastTable, lastTable);
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMatch() {
        /*
            pattern: sell
            text: She sells seashells by the seashore.
            indices: 4
            expected total comparisons: 20
         */
        assertEquals(sellAnswer,
                PatternMatching.boyerMoore(sellPattern, sellText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 20.", 20, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreNoMatch() {
        /*
            pattern: sell
            text: sea lions trains cardinal boardwalk
            indices: -
            expected total comparisons: 9
         */
        assertEquals(emptyList,
                PatternMatching.boyerMoore(sellPattern,
                        sellNoMatch, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 9.", 9, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
            expected total comparisons: 5
         */
        assertEquals(multipleAnswer,
                PatternMatching.boyerMoore(multiplePattern,
                        multipleText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreLongerText() {
        /*
            pattern: sea lions trains cardinal boardwalk
            text: sell
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList,
                PatternMatching.boyerMoore(sellNoMatch,
                        sellPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMatch() {
        /*
            pattern: sell
            text: She sells seashells by the seashore.
            indices: 4
            expected total comparisons: 4
         */
        assertEquals(sellAnswer,
                PatternMatching.rabinKarp(sellPattern, sellText, comparator));
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 4.", 4, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpNoMatch() {
        /*
            pattern: sell
            text: sea lions trains cardinal boardwalk
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList,
                PatternMatching.rabinKarp(sellPattern,
                        sellNoMatch, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpMultipleMatches() {
        /*
            pattern: ab
            text: abab
            indices: 0, 2
            expected total comparisons: 4
         */
        assertEquals(multipleAnswer,
                PatternMatching.rabinKarp(multiplePattern,
                        multipleText, comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 4.", 4, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpLongerText() {
        /*
            pattern: sea lions trains cardinal boardwalk
            text: sell
            indices: -
            expected total comparisons: 0
         */
        assertEquals(emptyList,
                PatternMatching.rabinKarp(sellNoMatch,
                        sellPattern, comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpEqualHash() {
        /*
            These are characters with ASCII values as shown, not the actual
            characters shown. Most do not have actual characters.

            pattern: 011
            text: 00(114)011
            indices: 2
            expected total comparisons: 5
         */
        List<Integer> answer = new ArrayList<>();
        answer.add(3);
        assertEquals(answer,
                PatternMatching.rabinKarp("\u0000\u0001\u0001",
                        "\u0000\u0000\u0072\u0000\u0001\u0001", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 5.", 5, comparator.getComparisonCount());
    }

    @Test
    public void testBuildFailureTableCase3() {
        /*
            pattern: abaababa
            failure table: [0, 0, 1, 1, 2, 3, 2, 3]
            comparisons: 9
         */
        int[] failureTable = PatternMatching.buildFailureTable("abaababa", comparator);
        int[] expected = {0, 0, 1, 1, 2, 3, 2, 3};
        assertArrayEquals(expected, failureTable);
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 9.", 9, comparator.getComparisonCount());
    }

    @Test
    public void testPatternLongerThanTextKMP() {
        /*
            pattern: abaababa
            text: aba
            comparisons: 0
         */
        List<Integer> kmpResult = PatternMatching.kmp("abaababa", "aba", comparator);
        assertTrue(kmpResult.isEmpty());
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 0.", 0, comparator.getComparisonCount());
    }

    @Test
    public void testKMPMatchCase3() {
        /*
            pattern: abaababa
            text: abaababaaabaababa
            indices: 0, 9
            expected total comparison: 28 (19 from searching, 9 from failure table)
            failure table: [0, 0, 1, 1, 2, 3, 2, 3]
            comparisons: 9
        0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10| 11| 12| 13| 14| 15| 16
        a | b | a | a | b | a | b | a | a | a | b | a | a | b | a | b | a
        --+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---
        a | b | a | a | b | a | b | a |   |   |   |   |   |   |   |   |
        - | - | - | - | - | - | - | - |   |   |   |   |   |   |   |   |     comparisons: 8
          |   |   |   |   | a | b | a | a | b | a | b | a |   |   |   |
          |   |   |   |   |   |   |   | - | - |   |   |   |   |   |   |     comparisons: 2
          |   |   |   |   |   |   |   | a | b | a | a | b | a | b | a |
          |   |   |   |   |   |   |   |   | - |   |   |   |   |   |   |     comparisons: 1
          |   |   |   |   |   |   |   |   | a | b | a | a | b | a | b | a
          |   |   |   |   |   |   |   |   | - | - | - | - | - | - | - | -   comparisons: 8
         comparisons: 19
         */
        List<Integer> thisAnswer = new ArrayList<>(2);
        thisAnswer.add(0);
        thisAnswer.add(9);
        assertEquals(thisAnswer,
                PatternMatching.kmp("abaababa", "abaababaaabaababa", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 28.", 28, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpSameCharacters() {
        /*
        Checks that hashing is correct and takes into account the order that it reads the
        characters.
         */
        List<Integer> thisAnswer = new ArrayList<>(1);
        thisAnswer.add(5);
        assertEquals(thisAnswer,
                PatternMatching.rabinKarp("abc", "cbacbabc", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 3.", 3, comparator.getComparisonCount());
    }

    @Test
    public void patternInsidePatternRabinKarp() {
        /*
        Pattern overlaps with itself, so will need to not skip over previously checked pattern,
        but only increment by one, causing it to check the 'a' at index 1 twice.
         */
        List<Integer> thisAnswer = new ArrayList<>(1);
        thisAnswer.add(0);
        thisAnswer.add(1);
        assertEquals(thisAnswer, PatternMatching.rabinKarp("aa", "aaa", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 4.", 4, comparator.getComparisonCount());
    }

    @Test
    public void skipImpossibleCharacterBoyerMoore() {
        /*
        Compares from back, so first checked index will be index 2. Mismatch at index 2, so shift
         front of pattern to compare with index 3 of the text. Will only need to compare 4 times
         because of this.
         */
        List<Integer> thisAnswer = new ArrayList<>(1);
        thisAnswer.add(3);
        assertEquals(thisAnswer, PatternMatching.boyerMoore("abc", "abdabc", comparator));
        assertTrue("Did not use the comparator.",
                comparator.getComparisonCount() != 0);
        assertEquals("Comparison count was " + comparator.getComparisonCount()
                + ". Should be 4.", 4, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testLastTable() {
        CharSequence pattern1 = "abacab";
        Map<Character, Integer> expected1 = new HashMap<>();
        expected1.put('a', 4);
        expected1.put('b', 5);
        expected1.put('c', 3);

        assertEquals(expected1, PatternMatching.buildLastTable(pattern1));

        CharSequence pattern2 = "happy";
        Map<Character, Integer> expected2 = new HashMap<>();
        expected2.put('a', 1);
        expected2.put('h', 0);
        expected2.put('p', 3);
        expected2.put('y', 4);

        assertEquals(expected2, PatternMatching.buildLastTable(pattern2));
    }

    // example from https://gatech.instructure.com/courses/132344/files/folder/Slides_Videos/Module12?preview=14378223
    @Test(timeout = TIMEOUT)
    public void testBoyerMooreSingle1() {
        CharSequence pattern = "abacab";
        CharSequence text = "abacbabadcabacab";

        List<Integer> expected = new ArrayList<>();
        expected.add(10);

        List<Integer> indices = PatternMatching.boyerMoore(pattern, text,
                this.comparator);

        assertEquals(expected, indices);
        assertEquals(13, comparator.getComparisonCount());
    }

    // example from https://gatech.instructure.com/courses/132344/files/folder/Slides_Videos/Module12?preview=14378289
    @Test(timeout = TIMEOUT)
    public void testBoyerMooreSingle2() {
        CharSequence pattern = "happy";
        CharSequence text = "because im happy";

        List<Integer> expected = new ArrayList<>();
        expected.add(11);

        List<Integer> indices = PatternMatching.boyerMoore(pattern, text,
                this.comparator);

        assertEquals(expected, indices);
        assertEquals(8, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreMultiple() {
        CharSequence pattern = "bcabc";
        CharSequence text = "afghbcabcabcr";

        /*
        Pattern: "bcabc"
        LOT: a  b  c
             2  3  4
        Text:     a f g h b c a b c a b c r
        Pattern:  b c a b c                     1 comp, shift = 3, i = 0+4-3 = 1
                    b c a b c                   3 comp, shift = -1, i = 1+2-(-1) = 4
                          b c a b c             5 comp, match
                            b c a b c           1 comp, shift = 2, i=5+4-2=7
                                b c a b c       5 comp, match
                                  b c a b c     1 comp, end
         */

        List<Integer> expected = new ArrayList<>();
        expected.add(4);
        expected.add(7);

        List<Integer> indices = PatternMatching.boyerMoore(pattern, text,
                this.comparator);

        assertEquals(expected, indices);
        assertEquals(16, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreBestCase() {
        // this is the best case because the last letter in the pattern is
        // not contained in the text so we can always skip
        CharSequence pattern = "rock";
        CharSequence text = "the red roses grow in ireland";

        List<Integer> expected = new ArrayList<>();

        List<Integer> indices = PatternMatching.boyerMoore(pattern, text,
                this.comparator);

        assertEquals(expected, indices);
        assertEquals(7, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testBoyerMooreWorstCase() {
        // this is the worst case because we always mismatch on the first
        // character in the pattern, so Boyer Moore becomes brute force
        CharSequence pattern = "baaaa";
        CharSequence text = "aaaaaaaaaaabaaaaa";

        List<Integer> expected = new ArrayList<>();
        expected.add(11);

        List<Integer> indices = PatternMatching.boyerMoore(pattern, text,
                this.comparator);

        assertEquals(expected, indices);
        assertEquals(46, comparator.getComparisonCount());

    }

    @Test(timeout = TIMEOUT)
    public void testFailureTable1() {
        CharSequence pattern1 = "revararev";
        int[] expected1 = new int[]{0, 0, 0, 0, 1, 0, 1, 2, 3};
        assertArrayEquals(expected1, PatternMatching.buildFailureTable(pattern1,
                this.comparator));
        assertEquals(9, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testFailureTable2() {
        CharSequence pattern2 = "abacada";
        int[] expected2 = new int[]{0, 0, 1, 0, 1, 0, 1};
        assertArrayEquals(expected2, PatternMatching.buildFailureTable(pattern2,
                this.comparator));
        assertEquals(8, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testFailureTable3() {
        CharSequence pattern3 = "theatha";
        int[] expected3 = new int[]{0, 0, 0, 0, 1, 2, 0};
        assertArrayEquals(expected3, PatternMatching.buildFailureTable(pattern3,
                this.comparator));
        assertEquals(7, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPSingle() {
        CharSequence text = "ababacadabaabacabacaba";
        CharSequence pattern = "abacaba";

        List<Integer> expected = new ArrayList<>();

        expected.add(11);
        expected.add(15);

        // 7 comparisons for failure table, 27 comparisons for KMP algorithm

        assertEquals(expected, PatternMatching.kmp(pattern, text, this.comparator));
        assertEquals(34, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPMultipleMatches1() {
        CharSequence text = "acdefedefedefgbdefedef";
        CharSequence pattern = "defedef";

        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(6);
        expected.add(15);

        // 6 comparisons for failure table, 23 comparisons for KMP algorithm

        assertEquals(expected, PatternMatching.kmp(pattern, text, this.comparator));
        assertEquals(29, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPRepetitivePattern() {
        CharSequence text = "cccccaaaaacatacac";
        CharSequence pattern = "acatacac";

        List<Integer> expected = new ArrayList<>();
        expected.add(9);

        // 9 comparisons for failure table, 21 comparisons for KMP algorithm

        assertEquals(expected, PatternMatching.kmp(pattern, text, this.comparator));
        assertEquals(30, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testKMPWorstCase() {
        CharSequence text = "bbbabbbabbbbabbbabbba";
        CharSequence pattern = "bbbb";

        List<Integer> expected = new ArrayList<>();
        expected.add(8);

        // 3 comparisons for failure table, 33 comparisons for KMP algorithm

        assertEquals(expected, PatternMatching.kmp(pattern, text, this.comparator));
        assertEquals(36, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpWorstCase() {
        // this is the worst case because the hash always matches, so we have
        // to compare the pattern to every substring in the text
        CharSequence text = "aaaaaaaaaaaaaa";
        CharSequence pattern = "aaa";

        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            expected.add(i);
        }

        assertEquals(expected, PatternMatching.rabinKarp(pattern, text,
                this.comparator));
        assertEquals(36, comparator.getComparisonCount());
    }

    @Test(timeout = TIMEOUT)
    public void testRabinKarpBestCase() {
        // this is the best case because the hash of the text and pattern
        // match exactly twice when the pattern appears in the text
        CharSequence text = "cdbaedafagebcabdfage";
        CharSequence pattern = "fage";

        List<Integer> expected = new ArrayList<>();
        expected.add(7);
        expected.add(16);

        assertEquals(expected, PatternMatching.rabinKarp(pattern, text,
                this.comparator));
        assertEquals(8, comparator.getComparisonCount());
    }
}