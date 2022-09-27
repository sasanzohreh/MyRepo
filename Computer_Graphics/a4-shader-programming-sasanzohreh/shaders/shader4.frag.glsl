// Automatically provided by three.js
//
// uniform mat4 viewMatrix;
// uniform vec3 cameraPosition;
// uniform bool isOrthographic;

varying vec3 v_normal;
varying vec2 v_texcoord;

uniform vec3 u_color;
uniform sampler2D u_colorTexture; //background
uniform sampler2D u_colorImage; //greenScreen image
uniform vec3 u_ambient;
uniform float u_threshold;

void main() {
    // make up a light vector and use it for diffuse lighting
    /*vec3 light = vec3( 0.5, 0.2, 1.0 );
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
    //float width = 50.0;
    //float height = 50.0;
    vec2 onePixel = vec2(1.0, 1.0) / vec2(50.0, 50.0);
    vec4 pixelArr[5];
    bool allGreen = true;
    bool anyGreen = false;
    pixelArr[0] = texture2D(u_colorImage, v_texcoord); //middle pixel
    pixelArr[1] = texture2D(u_colorImage, v_texcoord + vec2(onePixel.x, 0.0));//right pixel
    pixelArr[2] = texture2D(u_colorImage, v_texcoord + vec2(-onePixel.x, 0.0));//left pixel
    pixelArr[3] = texture2D(u_colorImage, v_texcoord + vec2(0.0, onePixel.y));//top pixel
    pixelArr[4] = texture2D(u_colorImage, v_texcoord + vec2(0.0, -onePixel.y));//bottom pixel
    float padding = 0.05;
    vec4 greenScreen = vec4(0.0, 1.0, 0.0, 1.0);
    vec4 color = texture2D(u_colorImage, v_texcoord);
    //vec3 diff = color.xyz - greenScreen.xyz;
    for (int i = 0; i < 5; i++) {
        vec3 diff = pixelArr[i].xyz - greenScreen.xyz;
        if (!(diff.x * diff.x + diff.y * diff.y + diff.z * diff.z < u_threshold + padding)) {
            allGreen = false;
        } else if (diff.x * diff.x + diff.y * diff.y + diff.z * diff.z < u_threshold) {
            anyGreen = true;
        }
    }
    if (allGreen)
        color = texture2D(u_colorTexture, v_texcoord);
    else if (anyGreen)
        color = texture2D(u_colorTexture, v_texcoord) * .95 + texture2D(u_colorImage, v_texcoord) * .04;
    //if (diff.x * diff.x + diff.y * diff.y + diff.z * diff.z < u_threshold) {
    //    color = texture2D(u_colorTexture, v_texcoord);
    //}
    gl_FragColor = color;
}
