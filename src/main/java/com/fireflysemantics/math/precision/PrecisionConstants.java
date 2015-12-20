/**
  *  Licensed under the Apache License, Version 2.0 (the "License");
  *  you may not use this file except in compliance with the License.
  *  You may obtain a copy of the License at
  *
  *  http://www.apache.org/licenses/LICENSE-2.0
  *
  *  Unless required by applicable law or agreed to in writing, software
  *  distributed under the License is distributed on an "AS IS" BASIS,
  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  *  See the License for the specific language governing permissions and
  *  limitations under the License.
  */
package com.fireflysemantics.math.precision;

/**
 * Supports comparison of double values.
 */
public class PrecisionConstants {
	/**
	 * <p>
	 * Largest double-precision floating-point number such that
	 * {@code 1 + EPSILON} is numerically equal to 1. This value is an upper
	 * bound on the relative error due to rounding real numbers to double
	 * precision floating-point numbers.
	 * </p>
	 * <p>
	 * In IEEE 754 arithmetic, this is 2<sup>-53</sup>.
	 * </p>
	 *
	 * @see <a href="http://en.wikipedia.org/wiki/Machine_epsilon">Machine
	 *      epsilon</a>
	 */
	public static final double EPSILON;

	/**
	 * Safe minimum, such that {@code 1 / SAFE_MIN} does not overflow. <br/>
	 * In IEEE 754 arithmetic, this is also the smallest normalized number 2
	 * <sup>-1022</sup>.
	 */
	public static final double SAFE_MIN;

	/** Exponent offset in IEEE754 representation. */
	private static final long EXPONENT_OFFSET = 1023l;

	static {
		/*
		 * This was previously expressed as = 0x1.0p-53; However, OpenJDK (Sparc
		 * Solaris) cannot handle such small constants: MATH-721
		 */
		EPSILON = Double.longBitsToDouble((EXPONENT_OFFSET
				- 53l) << 52);

		/*
		 * This was previously expressed as = 0x1.0p-1022; However, OpenJDK
		 * (Sparc Solaris) cannot handle such small constants: MATH-721
		 */
		SAFE_MIN = Double.longBitsToDouble((EXPONENT_OFFSET
				- 1022l) << 52);
	}

	/**
	 * Private constructor.
	 */
	private PrecisionConstants() {
	}
}
