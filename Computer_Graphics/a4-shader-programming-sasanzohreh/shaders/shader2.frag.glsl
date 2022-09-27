// Automatically provided by three.js
//
// uniform mat4 viewMatrix;
// uniform vec3 cameraPosition;
// uniform bool isOrthographic;

varying vec3 v_normal;
varying vec2 v_texcoord;

uniform vec3 u_color;
uniform sampler2D u_colorTexture;
uniform vec3 u_ambient;
uniform float u_radius;

void main() {
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
    vec2 center1 = vec2(0.2, 0.8);
    vec2 center2 = vec2(0.5, 0.5);
    vec2 center3 = vec2(0.7, 0.6);
    if ((v_texcoord.x - center1.x) * (v_texcoord.x - center1.x) + (v_texcoord.y - center1.y) * (v_texcoord.y - center1.y) <= u_radius * u_radius)
        discard;
    if ((v_texcoord.x - center2.x) * (v_texcoord.x - center2.x) + (v_texcoord.y - center2.y) * (v_texcoord.y - center2.y) <= u_radius * u_radius)
        discard;
    if ((v_texcoord.x - center3.x) * (v_texcoord.x - center3.x) + (v_texcoord.y - center3.y) * (v_texcoord.y - center3.y) <= u_radius * u_radius)
        discard;
    gl_FragColor = tcolor;
}
