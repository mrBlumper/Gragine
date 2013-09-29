
attribute vec4 vPosition;
uniform mat4 uMatrix;
attribute vec4 vColor;
varying vec4 v_color;
void main() {
	v_color = vColor;
	gl_Position = uMatrix * vPosition;
}        