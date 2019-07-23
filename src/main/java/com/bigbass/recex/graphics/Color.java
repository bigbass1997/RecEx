/*******************************************************************************
 * Copyright 2011 LibGDX Authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bigbass.recex.graphics;

/**
 * <p>Utility class for color. Sections of this class are directly copied from com.badlogic.gdx.graphics.Color</p>
 * 
 * @see <a href="https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/Color.java">
 * https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/Color.java</a>
 */
public class Color {
	
	public static final Color
		RED = new Color(0xFF0000FF),
		BLUE = new Color(0x0000FFFF),
		GREEN = new Color(0x00FF00FF),
		BLACK = new Color(0x000000FF),
		WHITE = new Color(0xFFFFFFFF),
		ORANGE = new Color(0xFF8A00FF),
		HIGHLIGHT_LIGHT = new Color(0xFFFFFF55),
		HIGHLIGHT_DARK = new Color(0x00000044);
	
	/** the red, green, blue and alpha components **/
	public float r, g, b, a;
	
	public Color(){
		this(0x00000000);
	}
	
	/**
	 * Creates new Color object and sets the color using an integer value formated 0xRRGGBBAA
	 * 
	 * @param value integer based color
	 */
	public Color(int value){
		this.setRGBA(value);
	}
	
	/**
	 * Sets color based on an integer value formated 0xRRGGBBAA
	 * 
	 * @param value integer based color
	 */
	public void setRGBA(int value){
		r = ((value & 0xff000000) >>> 24) / 255f;
		g = ((value & 0x00ff0000) >>> 16) / 255f;
		b = ((value & 0x0000ff00) >>> 8) / 255f;
		a = ((value & 0x000000ff)) / 255f;
	}
	
	/**
	 * @return integer value of 0xRRGGBBAA
	 */
	public int toRGBA(){
		return ((int)(r * 255) << 24) | ((int)(g * 255) << 16) | ((int)(b * 255) << 8) | (int)(a * 255);
	}

	/**
	 * @return integer value of 0xAARRGGBB
	 */
	public int toARGB(){
		return ((int)(a * 255) << 24) | ((int)(r * 255) << 16) | ((int)(g * 255) << 8) | (int)(b * 255);
	}
	
	/** Clamps this Color's components to a valid range [0 - 1]
	 * @return this Color for chaining */
	public Color clamp () {
		if (r < 0)
			r = 0;
		else if (r > 1) r = 1;

		if (g < 0)
			g = 0;
		else if (g > 1) g = 1;

		if (b < 0)
			b = 0;
		else if (b > 1) b = 1;

		if (a < 0)
			a = 0;
		else if (a > 1) a = 1;
		return this;
	}
	
	public Color copy(){
		return new Color(this.toRGBA());
	}
	
	@Override
	public String toString(){
		return a + ", " + r + ", " + g + ", " + b;
	}
}
