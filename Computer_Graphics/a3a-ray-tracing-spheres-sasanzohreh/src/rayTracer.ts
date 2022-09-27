// classes you may find useful.  Feel free to change them if you don't like the way
// they are set up.

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

class Camera {
    //public forward: Vector;
    //public right: Vector;
    public u: Vector
    public v: Vector
    public w: Vector
    constructor(public pos: Vector, public lookAt: Vector, public up: Vector) {
        //var down = new Vector (0, -1, 0);
        //this.forward = Vector.norm(Vector.minus(lookAt, this.pos));
        //this.right = Vector.times(1.5, Vector.norm(Vector.cross(this.forward, up)));
        //this.up = Vector.times(1.5, Vector.norm(Vector.cross(this.forward, this.right)));
        this.w = Vector.norm(Vector.minus(this.pos, lookAt));
        //if (Vector.cross(this.up, this.w).x == 0 && Vector.cross(this.up, this.w).y == 0 && Vector.cross(this.up, this.w).z == 0)
        //this.u = Vector.cross(this.up, this.w)
        //else
        this.u = Vector.norm(Vector.cross(this.up, this.w))
        //if (Vector.cross(this.w, this.u).x == 0 && Vector.cross(this.w, this.u).y == 0 && Vector.cross(this.w, this.u).z == 0)
        //this.v = Vector.cross(this.w, this.u)
        //else 
        this.v = Vector.norm(Vector.cross(this.w, this.u))
    }
}

class Light {
    public pos: Vector;
    public color: Color;

    constructor(pos: Vector, color: Color) {
        this.pos = pos
        this.color = color
    }
}

interface Scene {
    objects: Sphere[];
    lights: Light[];
    camera: Camera;
    ambient: Color;
    ambientSet: number;
    background: Color;
    d: number
}

class Sphere{
    //public radius2: number;
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

    constructor(x: number, y: number, z: number, radius: number, 
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

    // div is the HTMLElement we'll add our canvas to
    // width, height are the size of the canvas
    // screenWidth, screenHeight are the number of pixels you want to ray trace
    //  (recommend that width and height are multiples of screenWidth and screenHeight)
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
        this.scene.ambient = Color.white
        this.scene.ambientSet = 0
        this.scene.lights = []
        this.scene.objects = []
        this.scene.camera = new Camera (new Vector(0,0,0), new Vector (0,0,-1), new Vector (0,1,0))
        this.set_fov(90)
        //this.scene.camera.d = 1 / Math.tan(90 * this.DEG2RAD / 2)
        this.scene.background = Color.white
    }

    // API Functions you should implement

    // clear out all scene contents
    reset_scene() {
        this.scene.ambient = Color.white
        this.scene.ambientSet = 0
        this.scene.lights = []
        this.scene.objects = []
        this.scene.camera = new Camera (new Vector(0,0,0), new Vector (0,0,-1), new Vector (0,1,0))
        this.set_fov(90)
        //this.scene.camera.d = 1 / Math.tan(90 * this.DEG2RAD / 2)
        this.scene.background = Color.white
    }

    // create a new point light source
    new_light (r: number, g: number, b: number, x: number, y: number, z: number) {
        let light = new Light (new Vector (x, y ,z), new Color (r, g, b))
        //light.color = new Color (r, g, b)
        //light.pos = new Vector (x, y ,z)
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
        //console.log(this.scene.d)
    }

    // set the virtual camera's position and orientation
    // x1,y1,z1 are the camera position
    // x2,y2,z2 are the lookat position
    // x3,y3,z3 are the up vector
    set_eye(x1: number, y1: number, z1: number, 
            x2: number, y2: number, z2: number, 
            x3: number, y3: number, z3: number) {
        this.scene.camera = new Camera(new Vector(x1, y1, z1), new Vector(x2, y2, z2), new Vector(x3, y3, z3))
    }

    // create a new sphere
    new_sphere (x: number, y: number, z: number, radius: number, 
                dr: number, dg: number, db: number, 
                k_ambient: number, k_specular: number, specular_pow: number) {
        this.scene.objects.push(new Sphere(x, y, z, radius, dr, dg, db, k_ambient, k_specular, specular_pow))
    }

    // INTERNAL METHODS YOU MUST IMPLEMENT

    // create an eye ray based on the current pixel's position
    private eyeRay(i: number, j: number): Ray {
        let start = Vector.times(1,this.scene.camera.pos)
        let u_s = -1 + 2 * i / this.screenWidth
        let v_s = -1 + 2 * j / this.screenHeight
        let direction = Vector.norm(Vector.plus(Vector.plus(Vector.times(-this.scene.d, this.scene.camera.w), Vector.times(this.screenWidth/this.screenHeight, Vector.times(u_s, this.scene.camera.u))), Vector.times(-v_s, this.scene.camera.v)))
        /*let ray = {
            start: start,
            dir: direction
        }*/
        //console.log(direction)
        return {start: start, dir: direction}
    }

    private traceRay(ray: Ray, depth: number = 0): Color {
        let t_closest = -1
        let currentColor: Color = null as any
        this.scene.objects.forEach(object => {
            //check intersection
            let d = Vector.times(1,ray.dir)
            let e = Vector.times(1,ray.start)
            let c = new Vector (object.x, object.y, object.z)
            let disc = (Math.pow(Vector.dot(d, Vector.minus(e, c)),2)) - (Vector.dot(d, d) * (Vector.dot(Vector.minus(e, c), Vector.minus(e, c)) - Math.pow(object.radius, 2)))
            //console.log(d)
            if (disc >= 0) {
                //console.log("test")
                let t_p = (Vector.dot(Vector.times(-1, d), Vector.minus(e, c)) + Math.sqrt(disc)) / Vector.dot(d, d)
                let t_m = (Vector.dot(Vector.times(-1, d), Vector.minus(e, c)) - Math.sqrt(disc)) / Vector.dot(d, d)
                let t_min = -1
                
                if (t_p < t_m && t_p >= 0 && (t_closest > t_p || t_closest == -1))
                    t_min = t_p
                else if(t_m < t_p && t_m >= 0 && (t_closest > t_m || t_closest == -1))
                    t_min = t_m
                //console.log(t_m)
                if (t_min != -1) {
                    t_closest = t_min
                    let hit = Vector.plus(ray.start, Vector.times(t_closest, ray.dir))
                    let normal = Vector.norm(Vector.times(1 / object.radius, Vector.minus(hit, c)))
                    let r = 0
                    let g = 0
                    let b = 0
                    this.scene.lights.forEach(light => {
                        let lightRay = Vector.norm(Vector.minus(light.pos, hit))
                        let V = Vector.norm(Vector.minus(this.scene.camera.pos,hit))
                        let reflect = Vector.norm(Vector.minus(Vector.times(2 * Vector.dot(lightRay, normal), normal), lightRay))
                        //if (Vector.dot(reflect, this.scene.camera.v) > 0 && Vector.dot(lightRay, normal) > 0) {
                        r += light.color.r * (object.dr * Math.max(0,Vector.dot(normal, lightRay)) + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                        g += light.color.g * (object.dg * Math.max(0,Vector.dot(normal, lightRay)) + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                        b += light.color.b * (object.db * Math.max(0,Vector.dot(normal, lightRay)) + object.k_specular * Math.pow(Math.max(0,Vector.dot(reflect, V)), object.specular_pow))
                        //} else {
                        //    r += light.color.r * (object.dr * Vector.dot(normal, lightRay))
                        //    g += light.color.g * (object.dg * Vector.dot(normal, lightRay))
                        //    b += light.color.b * (object.db * Vector.dot(normal, lightRay))
                        //}
                    });
                    if (this.scene.ambientSet == 1) {
                        //console.log("test")
                        r += object.k_ambient * this.scene.ambient.r * object.dr
                        g += object.k_ambient * this.scene.ambient.g * object.dg
                        b += object.k_ambient * this.scene.ambient.b * object.db
                    }
                    currentColor = new Color (r, g, b)
                }
                //let a_eq = Math.pow(t_p, 2) * Vector.dot(d, d)
                //let b_eq = Vector.dot(Vector.times(2, d), Vector.times(t_p, Vector.minus(e, c)))
                //let c_eq = Vector.dot(Vector.minus(e, c), Vector.minus(e, c)) - Math.pow(object.radius, 2)
            }
            //if intersection is closer record it
            //return color based on intersection
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
    draw_scene() {

        // rather than doing a for loop for y, we're going to draw each line in
        // an animationRequestFrame callback, so we see them update 1 by 1
        var pixelWidth = this.width / this.screenWidth;
        var pixelHeight = this.height / this.screenHeight;
        var y = 0;
        
        this.clear_screen();

        var renderRow = () => {
            for (var x = 0; x < this.screenWidth; x++) {

                var ray = this.eyeRay(x, y);
                var c = this.traceRay(ray);

                var color = Color.toDrawingColor(c)
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