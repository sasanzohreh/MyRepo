// abstract library
import { DrawingCommon } from './common';
import * as THREE from 'three'

// A class for our application state and functionality
class Drawing extends DrawingCommon {

    constructor (canv: HTMLElement) {
        super (canv)
        // @ts-ignore
        this.rocket = this.scene.rocket
        // @ts-ignore
        this.animatedThruster1 = this.scene.animatedThruster1
        // @ts-ignore
        this.animatedThruster2 = this.scene.animatedThruster2
        // @ts-ignore
        this.animatedNozzle = this.scene.animatedNozzle
        // @ts-ignore
        this.animatedTip = this.scene.animatedTip
    }
    rocket: THREE.Mesh;
    animatedThruster1: THREE.Mesh;
    animatedThruster2: THREE.Mesh;
    animatedNozzle: THREE.Mesh;
    animatedTip: THREE.Mesh;
    /*
	Set up the scene during class construction
	*/
	initializeScene(){
        const allObjects = new THREE.Group();
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
        material = new THREE.MeshPhongMaterial( { color: 0xffffff, flatShading: true, side: THREE.DoubleSide } );
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

        allObjects.add(objectRoot)

        geometry = new THREE.PlaneGeometry(60,60)
        material = new THREE.MeshPhongMaterial( { color: 0x005f00, flatShading: true, side: THREE.DoubleSide  } );
        var floor = new THREE.Mesh(geometry, material)
        floor.position.set(0,-4,2)
        floor.rotateX(90)
        allObjects.add(floor)

        geometry = new THREE.PlaneGeometry(250,250)
        // @ts-ignore
        material = new THREE.MeshBasicMaterial( { map: new THREE.TextureLoader().load('./assets/sky.jpg'), side: THREE.DoubleSide  } );
        var sky = new THREE.Mesh(geometry, material)
        sky.position.set(0, 75, 25)
        allObjects.add(sky)

        this.scene.add( allObjects );
        this.camera.lookAt(new THREE.Vector3())
        this.camera.translateZ(3)
        const dirLight1 = new THREE.DirectionalLight( 0xffffff );
		dirLight1.position.set( 1, 1, 1 );
		this.scene.add( dirLight1 );
        const dirLight2 = new THREE.DirectionalLight( 0x002288 );
		dirLight2.position.set( - 1, - 1, - 1 );
		this.scene.add( dirLight2 );
        const ambientLight = new THREE.AmbientLight( 0x222222 );
		this.scene.add( ambientLight );

        // @ts-ignore
        this.scene.rocket = objectRoot
        // @ts-ignore
        this.scene.animatedThruster1 = thruster1
        // @ts-ignore
        this.scene.animatedThruster2 = thruster2
        // @ts-ignore
        this.scene.animatedNozzle = nozzle
        // @ts-ignore
        this.scene.animatedTip = tip
    }

	/*
	Update the scene during requestAnimationFrame callback before rendering
	*/
	updateScene(time: DOMHighResTimeStamp){
        if (time/1000 < 10) {
            this.camera.position.y += time/100000
            this.rocket.position.y += time/100000
            if (time/1000 > 2.5 && time/1000 < 5.71) {
                this.rocket.rotateY(time/100000)
            }
            if (time/1000 > 6) {
                this.animatedThruster1.position.y -= time/100000
                this.animatedThruster2.position.y -= time/100000
                this.animatedThruster1.rotateZ(-time/1000000)
                this.animatedThruster2.rotateZ(time/1000000)
            }
            if (time/1000 > 8) {
                this.animatedNozzle.position.y -= time/100000
                this.camera.position.y += time/100000
                if (time/1000 > 8.5) {
                    this.animatedTip.position.y += time/100000
                    
                }
            }
        }
        if (time/1000 > 10 && time/1000 < 13) {
            this.camera.position.y -= time/100000
            this.animatedTip.rotateZ(time/100000)
            this.rocket.position.y -= time/100000
        }
    }
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