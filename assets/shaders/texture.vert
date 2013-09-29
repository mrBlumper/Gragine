attribute vec4 vPosition;
uniform mat4 uMatrix;
attribute vec2 aTexCoordinate;
varying vec2 v_TexCoordinate;
void main() {
	v_TexCoordinate = aTexCoordinate;
	gl_Position = uMatrix * vPosition;
}        