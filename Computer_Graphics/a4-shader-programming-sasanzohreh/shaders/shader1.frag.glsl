// Automatically provided by three.js
//
// uniform mat4 viewMatrix;
// uniform vec3 cameraPosition;
// uniform bool isOrthographic;

varying vec3 v_normal;
varying vec2 v_texcoord;
uniform float u_count;
uniform float u_time;
uniform float u_iteration;

void main() {
    /*float cx = floor(u_count * v_texcoord.x);
    float cy = floor(u_count * v_texcoord.y);
    float color = mod(cx + cy, 2.0);*/
    vec2 z = vec2(v_texcoord.x, v_texcoord.y);
    vec2 c = vec2(0, float(sin(u_time)));
    float k;
    for (int i = 0; i < 300; i++) {
        if (float(i) > u_iteration) {
            k = u_iteration;
            break;
        }
        float x_next = (z.x * z.x - z.y * z.y) + c.x;
        float y_next = (z.y * z.x + z.x * z.y) + c.y;
        if (x_next * x_next > 2.0 || y_next * y_next > 2.0) {
            k = float(i);
            break;
        }
        z.x = x_next;
        z.y = y_next;
    }
    vec3 color;
    if (k == u_iteration)
        color = vec3(0.0, 0.0, 0.0);
    else
        color = vec3(k / 20.0, 0.0, 0.0);
    gl_FragColor = vec4(color, 1.0);
    //gl_FragColor = vec4(color, color, color, 1.0);
    /*
    // make up a light vector and use it for diffuse lighting
    vec3 light = vec3( 0.5, 0.2, 1.0 );
    light = normalize( light );

    // dot product of light and sorface normal
    float dProd = max(0.0,dot( v_normal, light ));

    // calculate a gray scaling value from the texture, using the typical brightness formula of rgb
    vec4 tcolor = texture2D( u_colorTexture, v_texcoord );
    vec4 gray = vec4( vec3( tcolor.r * 0.3 + tcolor.g * 0.59 + tcolor.b * 0.11 ), 1.0 );

    // calculate the diffuse color by multiplying the surface color by the dot product
    vec4 diffuse = vec4( u_color, 1.0 ) * dProd;
    vec4 ambient = vec4( u_ambient, 1.0 );

    // final color is diffuse + ambient * the gray scale of the texture
    gl_FragColor = gray * (diffuse + ambient);*/
}
