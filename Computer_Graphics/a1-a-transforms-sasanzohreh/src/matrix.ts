// Matrix Commands (for you to write!)

// You should modify the routines listed below to complete the assignment.
// Feel free to define any classes, global variables and helper routines that
// you need.
var matrix:number[][]

// set the current matrix to the identity
function init()
{
    matrix = [[1, 0 , 0, 0],[0, 1, 0, 0], [0, 0, 1, 0], [0, 0, 0, 1]]
}

// multiply the current matrix by the translation
function translate(x: number, y: number, z: number)
{
    matrix = multMatrix(matrix, [[1,0,0,x],[0,1,0,y],[0,0,1,z],[0,0,0,1]])
}

// multiply the current matrix by the scale
function scale(x: number, y: number, z: number)
{
    matrix = multMatrix(matrix, [[x,0,0,0],[0,y,0,0],[0,0,z,0],[0,0,0,1]])
}

// multiply the current matrix by the rotation
function rotateX(angle: number)
{
    matrix = multMatrix(matrix, [[1,0,0,0],[0,Math.cos(angle * Math.PI / 180),-Math.sin(angle * Math.PI / 180),0],[0,Math.sin(angle * Math.PI / 180),Math.cos(angle * Math.PI / 180),0],[0,0,0,1]]) 
}

// multiply the current matrix by the rotation
function rotateY(angle: number)
{
    matrix = multMatrix(matrix, [[Math.cos(angle * Math.PI / 180),0,Math.sin(angle * Math.PI / 180),0],[0,1,0,0],[-Math.sin(angle * Math.PI / 180),0,Math.cos(angle * Math.PI / 180),0],[0,0,0,1]])
}

// multiply the current matrix by the rotation
function rotateZ(angle: number)
{
    matrix = multMatrix(matrix, 
        [[Math.cos(angle * Math.PI / 180),-Math.sin(angle * Math.PI / 180),0,0],
        [Math.sin(angle * Math.PI / 180),Math.cos(angle * Math.PI / 180),0,0],
        [0,0,1,0],
        [0,0,0,1]])
}

// print the current matrix
function print()
{
    // add code here!
    // use `console.log("something")` to print something to the browser console.
    console.log(matrix[0][0] + ", " + matrix[0][1] + ", " + matrix[0][2] + ", " + matrix[0][3])
    console.log(matrix[1][0] + ", " + matrix[1][1] + ", " + matrix[1][2] + ", " + matrix[1][3]);
    console.log(matrix[2][0] + ", " + matrix[2][1] + ", " + matrix[2][2] + ", " + matrix[2][3]);
    console.log(matrix[3][0] + ", " + matrix[3][1] + ", " + matrix[3][2] + ", " + matrix[3][3]);
    // end with a blank line!
    console.log("")
}

// return the current matrix as an array of 16 numbers
// in row major order (i.e., elements 0..3 are row 1, 4..7 are row2,
// 8..11 are row3, and 12..15 are row4)
function currentMatrix() : number[]
{
    var cmt:number[] = [matrix[0][0], matrix[0][1], matrix[0][2], matrix[0][3], matrix[1][0], matrix[1][1], matrix[1][2], matrix[1][3],
     matrix[2][0], matrix[2][1], matrix[2][2], matrix[2][3], matrix[3][0], matrix[3][1], matrix[3][2], matrix[3][3]]
    return cmt
}

function multMatrix(oldCmt:number[][], transformCmt:number[][]) : number[][] {
    var newCmt:number[][] = [[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]]
    
    for(var i = 0; i < 4; i++){
        for (var j = 0; j < 4; j++) {
            for (var k = 0; k < 4; k++){
                newCmt[i][j] += oldCmt[i][k] * transformCmt[k][j]
            }
        }
    }

    return newCmt
}

export {init, translate, scale, rotateX, rotateY, rotateZ, print, currentMatrix}
