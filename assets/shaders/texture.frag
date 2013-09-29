precision mediump float;
uniform sampler2D uTexture;
varying vec2 v_TexCoordinate;
void main() {
	gl_FragColor =  texture2D(uTexture, v_TexCoordinate);
}