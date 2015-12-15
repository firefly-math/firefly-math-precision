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

import java.math.BigDecimal;

/**
 * Supports comparison and rounding of double values.
 */
public class RoundDouble {

	/** Positive zero. */
	private static final double POSITIVE_ZERO = 0d;

	/**
	 * Private constructor.
	 */
	private RoundDouble() {
	}

	/**
	 * Rounds the given value to the specified number of decimal places. The
	 * value is rounded using the {@link BigDecimal#ROUND_HALF_UP} method.
	 *
	 * @param x
	 *            Value to round.
	 * @param scale
	 *            Number of digits to the right of the decimal point.
	 * @return the rounded value as a double.
	 */
	public static double round(double x, int scale) {
		return round(x, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * Rounds the given value to the specified number of decimal places. The
	 * value is rounded using the given method which is any method defined in
	 * {@link BigDecimal}. If {@code x} is infinite or {@code NaN}, then the
	 * value of {@code x} is returned unchanged, regardless of the other
	 * parameters.
	 *
	 * @param x
	 *            Value to round.
	 * @param scale
	 *            Number of digits to the right of the decimal point.
	 * @param roundingMethod
	 *            Rounding method as defined in {@link BigDecimal}.
	 * @return the rounded value.
	 * @throws ArithmeticException
	 *             if {@code roundingMethod == ROUND_UNNECESSARY} and the
	 *             specified scaling operation would require rounding.
	 * @throws IllegalArgumentException
	 *             if {@code roundingMethod} does not represent a valid rounding
	 *             mode.
	 */
	public static double round(double x, int scale, int roundingMethod) {
		try {
			final double rounded =
					(new BigDecimal(Double.toString(x)).setScale(scale, roundingMethod)).doubleValue();
			// Commons Math JIRA MATH-1089: negative values rounded to zero
			// https://issues.apache.org/jira/browse/MATH-1089
			// should result in negative zero
			return rounded == POSITIVE_ZERO ? POSITIVE_ZERO
					* x : rounded;
		} catch (NumberFormatException ex) {
			if (Double.isInfinite(x)) {
				return x;
			} else {
				return Double.NaN;
			}
		}
	}
}