//Sasan Zohreh
function lessEpsilon(num: number){ 
    return Math.abs(num) < 1e-10; 
} 
function greaterEpsilon(num: number){ 
    return Math.abs(num) > 1e-10; 
} 
  
// classes from the Typescript RayTracer sample
export class Vector {
    constructor(public x: number,
                public y: number,
                public z: number) {
    }
    static times(k: number, v: Vector) { return new Vector(k * v.x, k * v.y, k * v.z); }
    static minus(v1: Vector, v2: Vector) { return new Vector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z); }
    static plus(v1: Vector, v2: Vector) { return new Vector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z); }
    static dot(v1: Vector, v2: Vector) { return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z; }
    static mag(v: Vector) { return Math.sqrt(v.x * v.x + v.y * v.y + v.z * v.z); }
    static norm(v: Vector) {
        var mag = Vector.mag(v);
        var div = (mag === 0) ? Infinity : 1.0 / mag;
        return Vector.times(div, v);
    }
    static cross(v1: Vector, v2: Vector) {
        return new Vector(v1.y * v2.z - v1.z * v2.y,
                          v1.z * v2.x - v1.x * v2.z,
                          v1.x * v2.y - v1.y * v2.x);
    }
}

export class Color {
    constructor(public r: number,
                public g: number,
                public b: number) {
    }
    static scale(k: number, v: Color) { return new Color(k * v.r, k * v.g, k * v.b); }
    static plus(v1: Color, v2: Color) { return new Color(v1.r + v2.r, v1.g + v2.g, v1.b + v2.b); }
    static times(v1: Color, v2: Color) { return new Color(v1.r * v2.r, v1.g * v2.g, v1.b * v2.b); }
    static white = new Color(1.0, 1.0, 1.0);
    static grey = new Color(0.5, 0.5, 0.5);
    static black = new Color(0.0, 0.0, 0.0);
    static lightness(c: Color) { return Math.sqrt(c.r * c.r + c.g * c.g + c.b * c.b); }
    static toDrawingColor(c: Color) {
        var legalize = (d: number) => d > 1 ? 1 : d;
        return {
            r: Math.floor(legalize(c.r) * 255),
            g: Math.floor(legalize(c.g) * 255),
            b: Math.floor(legalize(c.b) * 255)
        }
    }
}

interface Ray {
    start: Vector;
    dir: Vector;
}

// a suggested interface for jitter samples
interface Sample {
    s: number,
    t: number
}

class Camera {
    public u: Vector
    public v: Vector
    public w: Vector
    constructor(public pos: Vector, public lookAt: Vector, public up: Vector) {
        this.w = Vector.norm(Vector.minus(this.pos, lookAt));
        this.u = Vector.norm(Vector.cross(this.up, this.w))
        this.v = Vector.norm(Vector.cross(this.w, this.u))
    }
}

class Light {
    public pos: Vector;
    public color: Color;
    public u: Vector;
    public v: Vector;
    constructor(pos: Vector, color: Color, u: Vector, v: Vector) {
        this.pos = pos
        this.color = color
        this.u = u
        this.v = v
    }
}

class sceneObject {
    public x: number 
    public y: number
    public z: number 
    public radius: number
    public dr: number
    public dg: number
    public db: number
    public k_ambient: number
    public k_specular: number
    public specular_pow: number
    public nx: number
    public ny: number
    public nz: number

    constructor(x: number, y: number, z: number, radius: number, 
        dr: number, dg: number, db: number, 
        k_ambient: number, k_specular: number, specular_pow: number, nx: number = 0, 
        ny: number = 0, nz: number = 0) {
        this.x = x
        this.y = y
        this.z = z
        this.radius = radius
        this.dr = dr
        this.dg = dg
        this.db = db
        this.k_ambient = k_ambient
        this.k_specular = k_specular
        this.specular_pow = specular_pow
        this.nx = nx
        this.ny = ny
        this.nz = nz
    }
    intersect(ray: Ray) : number {
        return -2
    }
    getNormal(hit: Vector, c: Vector){
        return Vector.norm(Vector.times(1 / this.radius, Vector.minus(hit, c)))
    }
}

interface Scene {
    objects: sceneObject[];
    lights: Light[];
    camera: Camera;
    ambient: Color;
    ambientSet: number;
    background: Color;
    d: number
}

class Sphere extends sceneObject{

    super(x: number, y: number, z: number, radius: number, 
        dr: number, dg: number, db: number, 
        k_ambient: number, k_specular: number, specular_pow: number) {
        this.x = x
        this.y = y
        this.z = z
        this.radius = radius
        this.dr = dr
        this.dg = dg
        this.db = db
        this.k_ambient = k_ambient
        this.k_specular = k_specular
        this.specular_pow = specular_pow
    }

    intersect(ray: Ray) : number {
        let t_closest = -1
        let d = Vector.times(1,ray.dir)
        let e = Vector.times(1,ray.start)
        let c = new Vector (this.x, this.y, this.z)
        let disc = (Math.pow(Vector.dot(d, Vector.minus(e, c)),2)) - (Vector.dot(d, d) * (Vector.dot(Vector.minus(e, c), Vector.minus(e, c)) - Math.pow(this.radius, 2)))
        if (disc >= 0) {
            let t_p = (Vector.dot(Vector.times(-1, d), Vector.minus(e, c)) + Math.sqrt(disc)) / Vector.dot(d, d)
            let t_m = (Vector.dot(Vector.times(-1, d), Vector.minus(e, c)) - Math.sqrt(disc)) / Vector.dot(d, d)
            let t_min = -1
            if (t_p < t_m && t_p > 1e-10 && (t_closest > t_p || t_closest == -1))
                t_min = t_p
            else if(t_m < t_p && t_m > 1e-10 && (t_closest > t_m || t_closest == -1))
                t_min = t_m
            if (t_min != -1) 
                t_closest = t_min
        }
        return t_closest
    }
    getNormal(hit: Vector, c: Vector){
        return Vector.norm(Vector.times(1 / this.radius, Vector.minus(hit, c)))
    }
}

class Disk extends sceneObject{
    super(x: number, y: number, z: number, radius: number, 
        dr: number, dg: number, db: number, 
        k_ambient: number, k_specular: number, specular_pow: number, 
        nx: number, ny: number, nz: number) {
        this.x = x
        this.y = y
        this.z = z
        this.nx = nx
        this.ny = ny
        this.nz = nz
        this.radius = radius
        this.dr = dr
        this.dg = dg
        this.db = db
        this.k_ambient = k_ambient
        this.k_specular = k_specular
        this.specular_pow = specular_pow
        
    }

    intersect(ray: Ray) : number {
        let t = -1
        let n = new Vector(this.nx, this.ny, this.nz)
        let center = new Vector(this.x, this.y, this.z)
        let denom = Vector.dot(n, ray.dir)
        //console.log(denom)
        if (denom > 1-6) {
            let p0l0 = Vector.minus(center, ray.start)
            t = Vector.dot(p0l0, n) / denom
            //console.log(t)
            if (t > 1e-6) {
                //console.log(t)
                let p = Vector.plus(ray.start, Vector.times(t, ray.dir))
                let v = Vector.minus(p, center)
                let d2 = Vector.dot(v, v)
                //console.log(Math.sqrt(d2))
                if (Math.sqrt(d2) <= this.radius)
                    return t
            }
        }
        return -1
    }
    getNormal(hit: Vector, c: Vector){
        return new Vector(this.nx, this.ny, this.nz)
    }
}

// A class for our application state and functionality
class RayTracer {
    // the constructor paramater "canv" is automatically created 
    // as a property because the parameter is marked "public" in the 
    // constructor parameter
    // canv: HTMLCanvasElement
    //
    // rendering context for the canvas, also public
    // ctx: CanvasRenderingContext2D

    // initial color we'll use for the canvas
    canvasColor = "lightyellow"

    canv: HTMLCanvasElement
    ctx: CanvasRenderingContext2D 
    scene: Scene = {
        ambient: new Color(0, 0, 0),
        ambientSet: 0,
        objects: [],
        lights: [],
        camera: new Camera (new Vector(0,0,0), new Vector (0,0,-1), new Vector (0,1,0)),
        background: new Color(0, 0, 0),
        d: 0
    }

    // some things that will get specified by user method calls
    enableShadows = true
    jitter = false
    samples = 1

    // user method calls set these, for the optional parts of the assignment
    enableBlur = false
    enableReflections = false
    enableDepth = false

    // if you are doing reflection, set some max depth here
    maxDepth = 5;

    constructor (div: HTMLElement,
        public width: number, public height: number, 
        public screenWidth: number, public screenHeight: number) {

        // let's create a canvas and to draw in
        this.canv = document.createElement("canvas");
        this.ctx = this.canv.getContext("2d")!;
        if (!this.ctx) {
            console.warn("our drawing element does not have a 2d drawing context")
            return
        }
        
        div.appendChild(this.canv);

        this.canv.id = "main";
        this.canv.style.width = this.width.toString() + "px";
        this.canv.style.height = this.height.toString() + "px";
        this.canv.width  = this.width;
        this.canv.height = this.height;
        this.canv.id = "main";
        this.canv.style.width = this.width.toString() + "px";
        this.canv.style.height = this.height.toString() + "px";
        this.canv.width  = this.width;
        this.canv.height = this.height;
        this.scene.ambient = Color.white
        this.scene.ambientSet = 0
        this.scene.lights = []
        this.scene.objects = []
        this.scene.camera = new Camera (new Vector(0,0,0), new Vector (0,0,-1), new Vector (0,1,0))
        this.set_fov(90)
        this.scene.background = Color.white
    }

    // HINT: SUGGESTED INTERNAL METHOD
    // create an array of samples (size this.samples ^ 2) in the range 0..1, which can
    // be used to create a distriubtion of rays around a single eye ray or light ray.
    // The distribution would use the jitter parameters to create either a regularly spaced or 
    // randomized set of samples.
    private createDistribution(): Sample[] {
        let dist = []
        //2d array
        if (this.jitter == true){
            for (let i = 0; i < this.samples; i++) {
                for (let j = 0; j < this.samples; j++) {
                    dist[i*this.samples + j] = {s: ((1 - 1/(this.samples)) - i * (2/this.samples)) + (Math.random() * 2/this.samples - 1/this.samples), 
                        t: (1 - 1/(this.samples)) - j * (2/this.samples) + (Math.random() * 2/this.samples - 1/this.samples)}
                }   
            }
        } else {
            for (let i = 0; i < this.samples; i++) {
                for (let j = 0; j < this.samples; j++) {
                    dist[i*this.samples + j] = {s: (1 - 1/(this.samples)) - i * (2/this.samples), t: (1 - 1/(this.samples)) - j * (2/this.samples)}
                }
            } //2 samples {(-.5,-.5),(-.5,.5),(.5,-.5),(.5,.5)} 1/this.samples = 1/2 = .5 
            // 3 samples {(-2/3, -2/3), (-2/3, 0), } 
        }
        return dist
    }

    // HINT: SUGGESTED BUT NOT REQUIRED, INTERNAL METHOD
    // like traceRay, but returns on first hit. More efficient than traceRay for detecting if "in shadow"
    private testRay(ray: Ray) : number {
        let t_closest = -1
        this.scene.objects.forEach(object => {
            let t = object.intersect(ray)
            //console.log(t)
            if (t > 1e-10 && (t_closest == -1 || t < t_closest)){
                //console.log(t)
                t_closest = t
            }
        })
        return t_closest
    }

    // NEW COMMANDS FOR PART B

    // create a new disk 
    // 
    // NOTE:  the final vx, vy, vz are only needed for optional motion blur part, 
    // and are the velocity of the object. The object is moving from x,y,z - vx,vy,vz to x,y,z + vx,vy,vz 
    // during the time interval being rendered.
    new_disk (x: number, y: number, z: number, radius: number, 
              nx: number, ny: number, nz: number, dr: number, dg: number, db: number, 
              k_ambient: number, k_specular: number, specular_pow: number,
              vx?: number, vy?: number, vz?: number) {
        this.scene.objects.push(new Disk(x, y, z, radius, dr, dg, db, k_ambient, k_specular, specular_pow, nx, ny, nz))
    }

    // create a new area light source
    area_light (r: number, g: number, b: number, x: number, y: number, z: number, 
                ux: number, uy: number, uz: number, vx: number, vy: number, vz: number) {
        let light = new Light (new Vector (x, y ,z), new Color (r, g, b), new Vector(ux,uy,uz), new Vector(vx,vy,vz))
        this.scene.lights.push(light)
    }

    set_sample_level (num: number) {
        this.samples = num
    }

    jitter_on() {
        this.jitter = true
    }

    jitter_off() {
        this.jitter = false
    }

    // turn reflection on or off for extra credit reflection part
    reflection_on() {
        this.enableReflections = true
    }

    reflection_off() {
        this.enableReflections = false
    }

    // turn motion blur on or off for extra credit motion blur part
    blur_on() {
        this.enableBlur = true
    }

    blur_off() {
        this.enableBlur = false
    }

    // turn depth of field on or off for extra credit depth of field part
    depth_on() {
        this.enableDepth = true
    }

    depth_off() {
        this.enableDepth = false
    }

    // COMMANDS FROM PART A

    // clear out all scene contents
    reset_scene() {
        this.scene.ambient = Color.white
        this.scene.ambientSet = 0
        this.scene.lights = []
        this.scene.objects = []
        this.scene.camera = new Camera (new Vector(0,0,0), new Vector (0,0,-1), new Vector (0,1,0))
        this.set_fov(90)
        this.scene.background = Color.white
    }

    // create a new point light source
    new_light (r: number, g: number, b: number, x: number, y: number, z: number) {
        let light = new Light (new Vector (x, y ,z), new Color (r, g, b), new Vector(0,0,0), new Vector(0,0,0))
        this.scene.lights.push(light)
    }

    // set value of ambient light source
    ambient_light (r: number, g: number, b: number) {
        this.scene.ambient = new Color(r, g, b)
        this.scene.ambientSet = 1
    }

    // set the background color for the scene
    set_background (r: number, g: number, b: number) {
        this.scene.background = new Color(r, g, b)
    }

    // set the field of view
    DEG2RAD = (Math.PI/180)

    set_fov (theta: number) {
        this.scene.d = 1 / Math.tan(theta * this.DEG2RAD /2)
    }

    // // set the position of the virtual camera/eye
    // set_eye_position (x: number, y: number, z: number) {
    //     this.scene.camera.pos = new Vector(x,y,z)
    // }

    // set the virtual camera's viewing direction
    set_eye(x1: number, y1: number, z1: number, 
            x2: number, y2: number, z2: number, 
            x3: number, y3: number, z3: number) {
        this.scene.camera = new Camera(new Vector(x1, y1, z1), new Vector(x2, y2, z2), new Vector(x3, y3, z3))
    }

    // create a new sphere.
    //
    // NOTE:  the final vx, vy, vz are only needed for optional motion blur part, 
    // and are the velocity of the object. The object is moving from x,y,z - vx,vy,vz to x,y,z + vx,vy,vz 
    // during the time interval being rendered.

    new_sphere (x: number, y: number, z: number, radius: number, 
                dr: number, dg: number, db: number, 
                k_ambient: number, k_specular: number, specular_pow: number, 
                vx?: number, vy?: number, vz?: number) {
        this.scene.objects.push(new Sphere(x, y, z, radius, dr, dg, db, k_ambient, k_specular, specular_pow))
    }

    // INTERNAL METHODS YOU MUST IMPLEMENT

    // create an eye ray based on the current pixel's position
    private eyeRay(i: number, j: number): Ray {
        let start = Vector.times(1,this.scene.camera.pos)
        let u_s = -1 + 2 * i / this.screenWidth
        let v_s = -1 + 2 * j / this.screenHeight
        let direction = Vector.norm(Vector.plus(Vector.plus(Vector.times(-this.scene.d, this.scene.camera.w), Vector.times(this.screenWidth/this.screenHeight, Vector.times(u_s, this.scene.camera.u))), Vector.times(-v_s, this.scene.camera.v)))
        return {start: start, dir: direction}
    }

    private traceRay(ray: Ray, depth: number = 0): Color {
        let t_closest = -1
        let currentColor: Color = null as any
        this.scene.objects.forEach(object => {
            //check intersection
            t_closest = object.intersect(ray)
            if (t_closest >= 0 && (Math.abs(t_closest - this.testRay(ray)) < 1e-10)) {
                let c = new Vector(object.x, object.y, object.z)
                let hit = Vector.plus(ray.start, Vector.times(t_closest, ray.dir))
                let normal = object.getNormal(hit, c)
                let r = 0
                let g = 0
                let b = 0
                this.scene.lights.forEach(light => {
                    //if point light
                    if (light.u.x == 0 && light.u.y == 0 && light.u.z == 0 && light.v.x == 0 && light.v.y == 0 && light.v.z == 0){
                        let lightRay = Vector.norm(Vector.minus(light.pos, hit))
                        let V = Vector.norm(Vector.minus(this.scene.camera.pos,hit))
                        let reflect = Vector.norm(Vector.minus(Vector.times(2 * Vector.dot(lightRay, normal), normal), lightRay))
                        //only add if light ray not blocked
                        if (this.testRay({start:Vector.plus(hit, Vector.times(1e-10, normal)), dir:lightRay}) < 0) {
                            r += light.color.r * (object.dr * Math.max(0,Vector.dot(normal, lightRay)) + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                            g += light.color.g * (object.dg * Math.max(0,Vector.dot(normal, lightRay)) + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                            b += light.color.b * (object.db * Math.max(0,Vector.dot(normal, lightRay)) + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                        }
                    } else { //if area light
                        let vecs = this.createDistribution()
                        let maxSpec = 0
                        let maxReflect = new Vector(0,0,0)
                        let V = Vector.norm(Vector.minus(this.scene.camera.pos,hit))
                        let areaR = 0, areaG = 0, areaB = 0
                        for(let i = 0; i < Math.pow(this.samples, 2); i++) {
                            let newLightPos = Vector.plus(Vector.plus(light.pos, Vector.times(vecs[i].s, light.u)), Vector.times(vecs[i].t, light.v))
                            let lightRay = Vector.norm(Vector.minus(newLightPos, hit))
                            let reflect = Vector.norm(Vector.minus(Vector.times(2 * Vector.dot(lightRay, normal), normal), lightRay))
                            
                            //if (object.intersect({start: newLightPos, dir: lightRay}) <= this.testRay({start: newLightPos, dir: lightRay})) {
                            //if(this.testRay({start:hit, dir:Vector.norm(Vector.minus(hit, newLightPos))}) < 0) {
                            if(this.testRay({start:Vector.plus(hit, Vector.times(1e-10, normal)), dir:lightRay}) < 0) {
                                areaR += light.color.r * (object.dr * Math.max(0,Vector.dot(normal, lightRay)))// + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                                areaG += light.color.g * (object.dg * Math.max(0,Vector.dot(normal, lightRay)))// + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                                areaB += light.color.b * (object.db * Math.max(0,Vector.dot(normal, lightRay)))// + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                                if (maxSpec < Vector.dot(reflect, V)){
                                    maxSpec = Vector.dot(reflect, V)
                                    maxReflect = Vector.norm(Vector.minus(Vector.times(2 * Vector.dot(lightRay, normal), normal), lightRay))
                                }
                            }
                        }
                        areaR /= Math.pow(this.samples, 2)
                        areaG /= Math.pow(this.samples, 2)
                        areaB /= Math.pow(this.samples, 2)
                        r += object.k_specular * Math.pow(Math.max(0,Vector.dot(maxReflect, V)), object.specular_pow) + areaR
                        g += object.k_specular * Math.pow(Math.max(0,Vector.dot(maxReflect, V)), object.specular_pow) + areaG
                        b += object.k_specular * Math.pow(Math.max(0,Vector.dot(maxReflect, V)), object.specular_pow) + areaB
                    }
                });
                if (this.scene.ambientSet == 1) {
                    r += object.k_ambient * this.scene.ambient.r * object.dr
                    g += object.k_ambient * this.scene.ambient.g * object.dg
                    b += object.k_ambient * this.scene.ambient.b * object.db
                }
                currentColor = new Color (r, g, b)
            }
        });
        if (currentColor != null)
            return currentColor
        return this.scene.background
    }

    // draw_scene is provided to create the image from the ray traced colors. 
    // 1. it renders 1 line at a time, and uses requestAnimationFrame(render) to schedule 
    //    the next line.  This causes the lines to be displayed as they are rendered.
    // 2. it uses the additional constructor parameters to allow it to render a  
    //    smaller # of pixels than the size of the canvas
    //
    // YOU WILL NEED TO MODIFY draw_scene TO IMPLEMENT DISTRIBUTION RAY TRACING!
    //
    // NOTE: this method now has three optional parameters that are used for the depth of
    // field extra credit part. You will use these to modify this routine to adjust the
    // eyeRays to create the depth of field effect.
    draw_scene(lensSize?: number, depth1?: number, depth2?: number) {

        // rather than doing a for loop for y, we're going to draw each line in
        // an animationRequestFrame callback, so we see them update 1 by 1
        var pixelWidth = this.width / this.screenWidth;
        var pixelHeight = this.height / this.screenHeight;
        var y = 0;
        
        this.clear_screen();

        var renderRow = () => {
            for (var x = 0; x < this.screenWidth; x++) {
                // HINT: if you implemented "createDistribution()" above, you can use it here
                let vecs = this.createDistribution()
                let r = 0
                let b = 0
                let g = 0
                // HINT: you will need to loop through all the rays, if distribution is turned
                // on, and compute an average color for each pixel.
                for (let i = 0; i < Math.pow(this.samples,2); i++) {
                    var ray = this.eyeRay(x + vecs[i].s / 2, y + vecs[i].t / 2);
                    var c = this.traceRay(ray);
                    r += c.r
                    g += c.g
                    b += c.b
                }
                r /= Math.pow(this.samples,2)
                g /= Math.pow(this.samples,2)
                b /= Math.pow(this.samples,2)
                var color = Color.toDrawingColor(new Color(r, g, b))
                this.ctx.fillStyle = "rgb(" + String(color.r) + ", " + String(color.g) + ", " + String(color.b) + ")";
                this.ctx.fillRect(x * pixelWidth, y * pixelHeight, pixelWidth+1, pixelHeight+1);
            }
            
            // finished the row, so increment row # and see if we are done
            y++;
            if (y < this.screenHeight) {
                // finished a line, do another
                requestAnimationFrame(renderRow);            
            } else {
                console.log("Finished rendering scene")
            }
        }

        renderRow();
    }

    clear_screen() {
        this.ctx.fillStyle = this.canvasColor;
        this.ctx.fillRect(0, 0, this.canv.width, this.canv.height);

    }
}
export {RayTracer}