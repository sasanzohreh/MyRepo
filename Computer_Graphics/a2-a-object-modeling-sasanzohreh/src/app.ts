// abstract library
import { DrawingCommon } from './common';
import * as THREE from 'three'

// A class for our application state and functionality
class Drawing extends DrawingCommon {

    constructor (canv: HTMLElement) {
        super (canv)
    }

    /*
	Set up the scene during class construction
	*/
	initializeScene(){
        const objectRoot = new THREE.Group();

        /*var geometry: THREE.BufferGeometry = new THREE.CylinderGeometry( 0, 1, 3, 10, 1 );
        var material = new THREE.MeshPhongMaterial( { color: 0x00ffff, flatShading: true } );
        var mesh = new THREE.Mesh( geometry, material );

        mesh.position.set(1,0,0);
        objectRoot.add( mesh );

        geometry = new THREE.TorusGeometry( 1, 0.2, 30, 40 );
        material = new THREE.MeshPhongMaterial( { color: 0xffff00, flatShading: true } );
        mesh = new THREE.Mesh( geometry, material );

        mesh.position.set(-1,0,0);
        objectRoot.add( mesh );*/
        
        //space ship cyclindrical center
        var geometry: THREE.BufferGeometry = new THREE.CylinderGeometry(1, 1, 3, 30, 1);
        var material = new THREE.MeshPhongMaterial( { color: 0xffffff, flatShading: true } );
        var meshBody = new THREE.Mesh( geometry, material );
        meshBody.position.set(0,-1,2);
        objectRoot.add( meshBody );

        //cone tip of ship
        const tip = new THREE.Group();
        geometry = new THREE.SphereGeometry(1, 30, 10, 0, Math.PI * 2, 0, Math.PI/2)
        material = new THREE.MeshPhongMaterial( { color: 0xffffff, flatShading: true } );
        var meshCone = new THREE.Mesh( geometry, material );
        tip.position.set(0,1.5,0)
        meshBody.add(tip);
        tip.add(meshCone);

        //side thrusters
        const thruster1 = new THREE.Group();
        const thruster2 = new THREE.Group();
        geometry = new THREE.CylinderGeometry(.5, .5, 2, 30, 1);
        material = new THREE.MeshPhongMaterial( { color: 0xffffff, flatShading: true } );
        var meshThrust1 = new THREE.Mesh(geometry, material);
        thruster1.position.set(1.5,-.5,0);
        var meshThrust2 = new THREE.Mesh(geometry, material);
        thruster2.position.set(-1.5,-.5,0);
        meshBody.add(thruster1);
        meshBody.add(thruster2);
        geometry = new THREE.SphereGeometry(.5, 30, 10, 0, Math.PI * 2, 0, Math.PI/2);
        material = new THREE.MeshPhongMaterial( { color: 0x5f5f5f, flatShading: true, side: THREE.DoubleSide } );
        var meshNoz1 = new THREE.Mesh(geometry, material);
        var meshNoz2 = new THREE.Mesh(geometry, material);
        geometry = new THREE.CylinderGeometry(0, .5, 1, 30, 1);
        material = new THREE.MeshPhongMaterial( { color: 0xffffff, flatShading: true } );
        var meshTip1 = new THREE.Mesh(geometry, material);
        var meshTip2 = new THREE.Mesh(geometry, material);
        thruster1.add(meshTip1)
        thruster2.add(meshTip2)
        meshTip1.position.set(0, 1.5, 0)
        meshTip2.position.set(0, 1.5, 0)
        thruster1.add(meshNoz1);
        thruster2.add(meshNoz2)
        meshNoz1.position.set(0,-1.25,0)
        meshNoz2.position.set(0,-1.25,0)
        thruster1.add(meshThrust1);
        //thruster1.scale.set(.9, .9, 1.05)
        thruster2.add(meshThrust2);

        //nozzle
        const nozzle = new THREE.Group();
        geometry = new THREE.SphereGeometry(1, 30, 10, 0, Math.PI * 2, 0, Math.PI/2)
        material = new THREE.MeshPhongMaterial( { color: 0x5f5f5f, flatShading: true, side: THREE.DoubleSide } );
        var meshNoz = new THREE.Mesh( geometry, material );
        nozzle.position.set(0,-2,0)
        meshBody.add(nozzle);
        nozzle.add(meshNoz);
        
        //fins
        const x = 0, y = 0;
        const fins = new THREE.Group();
        const fin = new THREE.Shape();
        fin.moveTo(x, y);
        fin.lineTo(x, y-1);
        fin.lineTo(x + 1, y - .5);
        fin.lineTo(x + 1, y + .5);
        fin.lineTo(x, y);
        geometry = new THREE.ShapeGeometry(fin)
        material = new THREE.MeshPhongMaterial( { color: 0x5f5f5f, flatShading: true, side: THREE.DoubleSide } );
        var meshFin1 = new THREE.Mesh(geometry, material)
        var meshFin2 = new THREE.Mesh(geometry, material)
        var meshFin3 = new THREE.Mesh(geometry, material)
        meshBody.add(fins);
        fins.add(meshFin1);
        fins.add(meshFin2)
        fins.add(meshFin3)
        fins.position.set(0, -.75, 0);
        fins.scale.set(1.1, 1.1,1.1)
        meshFin1.rotation.y = 31
        meshFin1.position.set(-1, 0, -1)
        meshFin2.position.set(1, 0, -1)
        meshFin2.rotation.y = 29
        meshFin3.position.set(0, 0, 1.5)
        meshFin3.rotation.y = 33

        objectRoot.rotation.x = 50.5

        this.scene.add( objectRoot );
    }

	/*
	Update the scene during requestAnimationFrame callback before rendering
	*/
	updateScene(time: DOMHighResTimeStamp){}
}

// a global variable for our state.  We implement the drawing as a class, and 
// will have one instance
var myDrawing: Drawing;

// main function that we call below.
// This is done to keep things together and keep the variables created self contained.
// It is a common pattern on the web, since otherwise the variables below woudl be in 
// the global name space.  Not a huge deal here, of course.

function exec() {
    // find our container
    var div = document.getElementById("drawing");

    if (!div) {
        console.warn("Your HTML page needs a DIV with id='drawing'")
        return;
    }

    // create a Drawing object
    myDrawing = new Drawing(div);
}

exec()