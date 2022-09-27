import {Drawing, Vector, Point} from "./common"
import {init_tests, draw_tests} from "./projection_test"

// A class for our application state and functionality
class MyDrawing extends Drawing {
    
    constructor (div: HTMLElement) {
        super(div)

        init_tests(this)
    }

    drawScene() {
        draw_tests(this)
    }

    // Matrix and Drawing Library implemented as part of this object

    // Begin by using the matrix transformation routines from part A of this project.
    // Make your current transformation matrix a property of this object.
    // You should modify the new routines listed below to complete the assignment.
    // Feel free to define any additional classes, class variables and helper methods
    // that you need.
    private matrix:number[][] = []
    private cpm:number[][] = []
    private begin:boolean = false
    private p1:Point = {x: NaN, y: NaN, z:NaN}

    beginShape() {
        this.begin = true
    }

    endShape() {
        this.p1.x = NaN
        this.p1.y = NaN
        this.p1.z = NaN
        this.begin = false
    }

    vertex(x: number, y: number, z: number) {
        if (this.begin) {
            if (isNaN(this.p1.x)){
                var vec:Vector = {x:x, y:y, z:z, w:1}
                vec = this.multVector(this.matrix, vec)
                //vec.x = vec.x / vec.w
                //vec.y = vec.y / vec.w
                //vec.z = vec.z / vec.w
                //vec.w = 1
                vec = this.multVector(this.cpm, vec)
                this.p1.x = vec.x / vec.w
                this.p1.y = vec.y / vec.w
                this.p1.z = vec.z / vec.w
            } else {
                var vec:Vector = {x:x, y:y, z:z, w:1}
                vec = this.multVector(this.matrix, vec)
                vec = this.multVector(this.cpm, vec)
                var p2:Point = {x: vec.x / vec.w, y: vec.y / vec.w, z: vec.z / vec.w}
                this.line(this.p1, p2)
                this.p1.x = NaN
                this.p1.y = NaN
                this.p1.z = NaN
            }
        }
    }

    perspective(fov: number, near: number, far: number) {
        var k:number = Math.tan(fov / 2 * Math.PI / 180) * near
        var bottom:number = -k
        var top:number = k
        if (top < bottom) {
            top *= -1
            bottom *= -1
        }
        
        var left:number = -k * (this.canv.width / this.canv.height)
        var right:number = k * (this.canv.width / this.canv.height)
        if (right < left) {
            right *= -1
            left *= -1
        }

        var per:number[][] = [[2 * near / (right - left), 0, ((left + right)/(left - right)), 0], 
            [0, 2 * near / (top - bottom), (bottom + top)/(bottom - top), 0], 
                [0, 0, (far + near)/(near - far), (2 * far * near)/(far - near)], [0, 0, 1, 0]]
        var vp:number[][] = [[this.canv.width/2, 0, 0, (this.canv.width - 1)/2], [0, this.canv.height/2, 0, (this.canv.height - 1)/2], [0,0,1,0], [0,0,0,1]]
        this.cpm = this.multMatrix(vp, per)
        //console.log(per)
    }

    ortho( left: number, right: number, top: number, bottom: number, 
        near: number, far: number ) {
        var ortho:number[][] = [[2/(right - left), 0, 0, -(right + left)/(right - left)], 
            [0, 2/(top - bottom), 0, -(top + bottom)/(top - bottom)], 
                [0, 0, 2/(near - far), -(near + far)/(near - far)], [0, 0, 0, 1]]
        var vp:number[][] = [[this.canv.width/2, 0, 0, (this.canv.width - 1)/2], [0, this.canv.height/2, 0, (this.canv.height - 1)/2], [0,0,1,0], [0,0,0,1]]
        this.cpm = this.multMatrix(vp, ortho)
	}

    initMatrix() // was init()
    {
        this.matrix = [[1, 0 , 0, 0],[0, 1, 0, 0], [0, 0, 1, 0], [0, 0, 0, 1]]
    }
    
    // mutiply the current matrix by the translation
    translate(x: number, y: number, z: number)
    {
        this.matrix = this.multMatrix(this.matrix, [[1,0,0,x],[0,1,0,y],[0,0,1,z],[0,0,0,1]])
    }
    
    // mutiply the current matrix by the scale
    scale(x: number, y: number, z: number)
    {
        this.matrix = this.multMatrix(this.matrix, [[x,0,0,0],[0,y,0,0],[0,0,z,0],[0,0,0,1]])
    }
    
    // mutiply the current matrix by the rotation
    rotateX(angle: number)
    {
        this.matrix = this.multMatrix(this.matrix, [[1,0,0,0],
            [0,Math.cos(angle * Math.PI / 180),-Math.sin(angle * Math.PI / 180),0],
            [0,Math.sin(angle * Math.PI / 180),Math.cos(angle * Math.PI / 180),0],
            [0,0,0,1]])
    }
    
    // mutiply the current matrix by the rotation
    rotateY(angle: number)
    {
        this.matrix = this.multMatrix(this.matrix, 
            [[Math.cos(angle * Math.PI / 180),0,Math.sin(angle * Math.PI / 180),0],
            [0,1,0,0],
            [-Math.sin(angle * Math.PI / 180),0,Math.cos(angle * Math.PI / 180),0],
            [0,0,0,1]])
    }
    
    // mutiply the current matrix by the rotation
    rotateZ(angle: number)
    {
        this.matrix = this.multMatrix(this.matrix, 
            [[Math.cos(angle * Math.PI / 180),
            -Math.sin(angle * Math.PI / 180),0,0],
            [Math.sin(angle * Math.PI / 180),
                Math.cos(angle * Math.PI / 180),0,0],
                [0,0,1,0],
                [0,0,0,1]])
    }

    printMatrix() // was print
    {
        console.log(this.matrix[0][0] + ", " + this.matrix[0][1] + ", " + this.matrix[0][2] + ", " + this.matrix[0][3])
        console.log(this.matrix[1][0] + ", " + this.matrix[1][1] + ", " + this.matrix[1][2] + ", " + this.matrix[1][3]);
        console.log(this.matrix[2][0] + ", " + this.matrix[2][1] + ", " + this.matrix[2][2] + ", " + this.matrix[2][3]);
        console.log(this.matrix[3][0] + ", " + this.matrix[3][1] + ", " + this.matrix[3][2] + ", " + this.matrix[3][3]);
        console.log("")
    }

    multMatrix(oldCmt:number[][], transformCmt:number[][]) : number[][] {
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

    multVector(cmt:number[][], vect:Vector) : Vector {
        var temp:number[] = []
        for(var i = 0; i < 4; i++){
            temp[i] = cmt[i][0] * vect.x + cmt[i][1] * vect.y + cmt[i][2] * vect.z + cmt[i][3] * vect.w
        }
        vect.x = temp[0]
        vect.y = temp[1]
        vect.z = temp[2]
        vect.w = temp[3]
        return vect
    }
}

// a global variable for our state
var myDrawing: MyDrawing

// main function, to keep things together and keep the variables created self contained
function exec() {
    // find our container
    var div = document.getElementById("drawing");

    if (!div) {
        console.warn("Your HTML page needs a DIV with id='drawing'")
        return;
    }

    // create a Drawing object
    myDrawing = new MyDrawing(div);
    myDrawing.render()
}

exec()