using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ChemistryBehavior : MonoBehaviour
{

    [SerializeField]
    private GameObject red;
    [SerializeField]
    private GameObject blue;
    [SerializeField]
    private GameObject green;

    [SerializeField]
    private GameObject pickup;
    [SerializeField]
    private GameObject pickupFill;

    [SerializeField]
    private Camera myCam;

    [SerializeField]
    private Animator redPotionPour;
    [SerializeField]
    private Animator bluePotionPour;
    [SerializeField]
    private Animator greenPotionPour;

    [SerializeField]
    private GameObject redLiquid;
    [SerializeField]
    private GameObject blueLiquid;
    [SerializeField]
    private GameObject greenLiquid;

    [SerializeField]
    private Material redM;
    [SerializeField]
    private Material blueM;
    [SerializeField]
    private Material greenM;

    [SerializeField]
    private Material purpleM;

    [SerializeField]
    private Material yellowM;


    [SerializeField]
    private GameObject firstPour;
    [SerializeField]
    private GameObject secondPour;
    [SerializeField]
    private GameObject thirdPour;
    private Animator anim;

    int[] correctOrder = new int[] {2, 3, 1};
    public int[] playersOrder = new int[3];

    Vector3 originalRedPosition;
    Vector3 originalBluePosition;
    Vector3 originalGreenPosition;
    Vector3 originalPickup;

    int counter;
    bool addedRed;
    bool addedBlue;
    bool addedGreen;
    public bool allMixed;
    public Material mat;
    public int kills;


    // Start is called before the first frame update
    void Start() {
        originalRedPosition = red.transform.position;
        originalBluePosition = blue.transform.position;
        originalGreenPosition = green.transform.position;
        if (pickup != null)
            originalPickup = pickup.transform.position;
        counter = 0;
        addedRed = false;
        addedBlue = false;
        addedGreen = false;
        allMixed = false;

        //potionPour = GetComponent<Animator>();

    }


    // red = 1
    // blue = 2
    // green = 3
    void Awake() {
        anim = GetComponent<Animator>();
        if (anim == null)
            Debug.Log("Animator could not be found");
    }
    //correct order is 2, 3, 1

    // Update is called once per frame
    void Update() {
        
        //RaycastHit raycastHit;

        if (!addedRed && Input.GetKeyDown(KeyCode.R)) {
            redPotionPour.SetBool("PourPotion", true);
            playersOrder[counter] = 1;
            counter++;
            addedRed = true;
            anim.SetBool("Interact", true);
        }

        if (redPotionPour.GetCurrentAnimatorStateInfo(0).IsName("Post Pour")) {
            redLiquid.SetActive(false);

            if (counter == 1) {
                firstPour.GetComponent<Renderer>().material = redM;
                firstPour.SetActive(true);
            } else if (counter == 2) {
                secondPour.SetActive(true);
                firstPour.SetActive(false);
                secondPour.GetComponent<Renderer>().material = purpleM;
            } else if (counter == 3) {
                thirdPour.SetActive(true);
                secondPour.SetActive(false);
                thirdPour.GetComponent<Renderer>().material = yellowM;
                counter++;
            }
        }

        if (!addedBlue && Input.GetKeyDown(KeyCode.B)) {
            bluePotionPour.SetBool("PourPotion", true);
            playersOrder[counter] = 2;
            counter++;
            addedBlue = true;
            anim.SetBool("Interact", true);
        }

        if (bluePotionPour.GetCurrentAnimatorStateInfo(0).IsName("Post Pour")) {
            blueLiquid.SetActive(false);

            if (counter == 1) {
                firstPour.GetComponent<Renderer>().material = blueM;
                firstPour.SetActive(true);
            } else if (counter == 2) {
                secondPour.SetActive(true);
                firstPour.SetActive(false);
                secondPour.GetComponent<Renderer>().material = purpleM;
            } else if (counter == 3) {
                thirdPour.SetActive(true);
                secondPour.SetActive(false);
                thirdPour.GetComponent<Renderer>().material = yellowM;
                //sets counter to 4
                counter++;
            }
        }

        if (!addedGreen && Input.GetKeyDown(KeyCode.G)) {
            greenPotionPour.SetBool("PourPotion", true);
            playersOrder[counter] = 3;
            counter++;
            addedGreen = true;
            anim.SetBool("Interact", true);
        }

        if (greenPotionPour.GetCurrentAnimatorStateInfo(0).IsName("Post Pour")) {
            greenLiquid.SetActive(false);

            if (counter == 1) {
                firstPour.GetComponent<Renderer>().material = greenM;
                firstPour.SetActive(true);
            } else if (counter == 2) {
                secondPour.SetActive(true);
                firstPour.SetActive(false);
                thirdPour.GetComponent<Renderer>().material = yellowM;
            } else if (counter == 3) {
                thirdPour.SetActive(true);
                secondPour.SetActive(false);
                thirdPour.GetComponent<Renderer>().material = purpleM;
                counter++;
            }
        }

        if (counter == 4 && Input.GetKeyDown(KeyCode.T)) {
            pickupFill.SetActive(true);
            pickupFill.GetComponent<Renderer>().material = thirdPour.GetComponent<Renderer>().material;
            allMixed = true;
            mat = thirdPour.GetComponent<Renderer>().material;
            anim.SetBool("Interact", true);
        }

        // if (Input.GetMouseButton(0)) {
        //     var ray = myCam.ScreenPointToRay(Input.mousePosition);
        //     if (Physics.Raycast(ray, out raycastHit, 100f)) {

        //         if (raycastHit.collider.tag == "red potion") {
        //             Vector3 position = red.transform.position;
        //             position.y = raycastHit.point.y;
        //             position.x = raycastHit.point.x;
        //             red.transform.position = position;

        //             if (!addedRed && Input.GetKeyDown(KeyCode.Space)) {
        //                 redPotionPour.SetBool("PourPotion", true);
        //                 playersOrder[counter] = 1;
        //                 counter++;
        //                 addedRed = true;
        //                 Debug.Log("Red: " + addedRed);
        //                 Debug.Log("Current counter: " + counter);
        //             }
 
        //             if (redPotionPour.GetCurrentAnimatorStateInfo(0).IsName("Post Pour")) {
        //                 redLiquid.SetActive(false);

        //                 if (counter == 1) {
        //                     firstPour.GetComponent<Renderer>().material = redM;
        //                     firstPour.SetActive(true);
        //                 } else if (counter == 2) {
        //                     secondPour.SetActive(true);
        //                     firstPour.SetActive(false);
        //                     secondPour.GetComponent<Renderer>().material = purpleM;
        //                 } else if (counter == 3) {
        //                     thirdPour.SetActive(true);
        //                     secondPour.SetActive(false);
        //                     thirdPour.GetComponent<Renderer>().material = yellowM;
        //                     counter++;
        //                 }
        //             }

        //         }

        //         if (raycastHit.collider.tag == "blue potion") {
        //             Debug.Log("Clicked on blue");
        //             Vector3 position = blue.transform.position;
        //             position.y = raycastHit.point.y;
        //             position.x = raycastHit.point.x;
        //             blue.transform.position = position;

        //             if (!addedBlue && Input.GetKeyDown(KeyCode.Space)) {
        //                 bluePotionPour.SetBool("PourPotion", true);
        //                 playersOrder[counter] = 2;
        //                 counter++;
        //                 addedBlue = true;
        //                 Debug.Log("Blue: " + addedBlue);
        //                 Debug.Log("Current counter: " + counter);
        //             }

        //             if (bluePotionPour.GetCurrentAnimatorStateInfo(0).IsName("Post Pour")) {
        //                 blueLiquid.SetActive(false);

        //                 if (counter == 1) {
        //                     firstPour.GetComponent<Renderer>().material = blueM;
        //                     firstPour.SetActive(true);
        //                 } else if (counter == 2) {
        //                     secondPour.SetActive(true);
        //                     firstPour.SetActive(false);
        //                     secondPour.GetComponent<Renderer>().material = purpleM;
        //                 } else if (counter == 3) {
        //                     thirdPour.SetActive(true);
        //                     secondPour.SetActive(false);
        //                     thirdPour.GetComponent<Renderer>().material = yellowM;
        //                     //sets counter to 4
        //                     counter++;
        //                 }
        //             }
        //         }

        //         if (raycastHit.collider.tag == "green potion") {
        //             Debug.Log("Clicked on green");
        //             Vector3 position = green.transform.position;
        //             position.y = raycastHit.point.y;
        //             position.x = raycastHit.point.x;
        //             green.transform.position = position;

        //             if (!addedGreen && Input.GetKeyDown(KeyCode.Space)) {
        //                 greenPotionPour.SetBool("PourPotion", true);
        //                 playersOrder[counter] = 3;
        //                 counter++;
        //                 addedGreen = true;
        //                 Debug.Log("Green: " + addedGreen);
        //                 Debug.Log("Current counter: " + counter);
        //             }

        //             if (greenPotionPour.GetCurrentAnimatorStateInfo(0).IsName("Post Pour")) {
        //                 greenLiquid.SetActive(false);

        //                 if (counter == 1) {
        //                     firstPour.GetComponent<Renderer>().material = greenM;
        //                     firstPour.SetActive(true);
        //                 } else if (counter == 2) {
        //                     secondPour.SetActive(true);
        //                     firstPour.SetActive(false);
        //                     thirdPour.GetComponent<Renderer>().material = yellowM;
        //                 } else if (counter == 3) {
        //                     thirdPour.SetActive(true);
        //                     secondPour.SetActive(false);
        //                     thirdPour.GetComponent<Renderer>().material = purpleM;
        //                     counter++;
        //                 }
        //             }
        //         }

        //         if (raycastHit.collider.tag == "pickup potion") {
        //             Vector3 position = pickup.transform.position;
        //             position.y = raycastHit.point.y;
        //             position.x = raycastHit.point.x;
        //             pickup.transform.position = position;

        //             //can only fill up if the player has put in all three potions
        //             if (counter == 4 && Input.GetKeyDown(KeyCode.Space)) {
        //                 pickupFill.SetActive(true);
        //                 pickupFill.GetComponent<Renderer>().material = thirdPour.GetComponent<Renderer>().material;
        //                 allMixed = true;
        //                 mat = thirdPour.GetComponent<Renderer>().material;
        //             }
        //         }

        //     }
        // }

        if (Input.GetMouseButtonUp(0)) {
            red.transform.position = originalRedPosition;
            blue.transform.position = originalBluePosition;
            green.transform.position = originalGreenPosition;
            if (pickup != null)
                pickup.transform.position = originalPickup;
        }

        if (counter == 4) {
            bool check = checkPlayer(playersOrder);
            Debug.Log("CHECK: " + check);
        }

    }


    public bool checkPlayer(int[] player) {

        for (int i = 0; i < 3; i++) {
            if (player[i] != correctOrder[i]) {
                return false;
            }
        }

        return true;

    }

}