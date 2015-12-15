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

import org.junit.Assert;
import org.junit.Test;

public class RoundDoubleTest {
	@Test
	public void testRoundDouble() {
		double x = 1.234567890;
		Assert.assertEquals(1.23, RoundDouble.round(x, 2), 0.0);
		Assert.assertEquals(1.235, RoundDouble.round(x, 3), 0.0);
		Assert.assertEquals(1.2346, RoundDouble.round(x, 4), 0.0);

		// JIRA MATH-151
		Assert.assertEquals(39.25, RoundDouble.round(39.245, 2), 0.0);
		Assert.assertEquals(39.24, RoundDouble.round(39.245, 2, BigDecimal.ROUND_DOWN), 0.0);
		double xx = 39.0;
		xx += 245d
				/ 1000d;
		Assert.assertEquals(39.25, RoundDouble.round(xx, 2), 0.0);

		// BZ 35904
		Assert.assertEquals(30.1d, RoundDouble.round(30.095d, 2), 0.0d);
		Assert.assertEquals(30.1d, RoundDouble.round(30.095d, 1), 0.0d);
		Assert.assertEquals(33.1d, RoundDouble.round(33.095d, 1), 0.0d);
		Assert.assertEquals(33.1d, RoundDouble.round(33.095d, 2), 0.0d);
		Assert.assertEquals(50.09d, RoundDouble.round(50.085d, 2), 0.0d);
		Assert.assertEquals(50.19d, RoundDouble.round(50.185d, 2), 0.0d);
		Assert.assertEquals(50.01d, RoundDouble.round(50.005d, 2), 0.0d);
		Assert.assertEquals(30.01d, RoundDouble.round(30.005d, 2), 0.0d);
		Assert.assertEquals(30.65d, RoundDouble.round(30.645d, 2), 0.0d);

		Assert.assertEquals(1.24, RoundDouble.round(x, 2, BigDecimal.ROUND_CEILING), 0.0);
		Assert.assertEquals(1.235, RoundDouble.round(x, 3, BigDecimal.ROUND_CEILING), 0.0);
		Assert.assertEquals(1.2346, RoundDouble.round(x, 4, BigDecimal.ROUND_CEILING), 0.0);
		Assert.assertEquals(-1.23, RoundDouble.round(-x, 2, BigDecimal.ROUND_CEILING), 0.0);
		Assert.assertEquals(-1.234, RoundDouble.round(-x, 3, BigDecimal.ROUND_CEILING), 0.0);
		Assert.assertEquals(-1.2345, RoundDouble.round(-x, 4, BigDecimal.ROUND_CEILING), 0.0);

		Assert.assertEquals(1.23, RoundDouble.round(x, 2, BigDecimal.ROUND_DOWN), 0.0);
		Assert.assertEquals(1.234, RoundDouble.round(x, 3, BigDecimal.ROUND_DOWN), 0.0);
		Assert.assertEquals(1.2345, RoundDouble.round(x, 4, BigDecimal.ROUND_DOWN), 0.0);
		Assert.assertEquals(-1.23, RoundDouble.round(-x, 2, BigDecimal.ROUND_DOWN), 0.0);
		Assert.assertEquals(-1.234, RoundDouble.round(-x, 3, BigDecimal.ROUND_DOWN), 0.0);
		Assert.assertEquals(-1.2345, RoundDouble.round(-x, 4, BigDecimal.ROUND_DOWN), 0.0);

		Assert.assertEquals(1.23, RoundDouble.round(x, 2, BigDecimal.ROUND_FLOOR), 0.0);
		Assert.assertEquals(1.234, RoundDouble.round(x, 3, BigDecimal.ROUND_FLOOR), 0.0);
		Assert.assertEquals(1.2345, RoundDouble.round(x, 4, BigDecimal.ROUND_FLOOR), 0.0);
		Assert.assertEquals(-1.24, RoundDouble.round(-x, 2, BigDecimal.ROUND_FLOOR), 0.0);
		Assert.assertEquals(-1.235, RoundDouble.round(-x, 3, BigDecimal.ROUND_FLOOR), 0.0);
		Assert.assertEquals(-1.2346, RoundDouble.round(-x, 4, BigDecimal.ROUND_FLOOR), 0.0);

		Assert.assertEquals(1.23, RoundDouble.round(x, 2, BigDecimal.ROUND_HALF_DOWN), 0.0);
		Assert.assertEquals(1.235, RoundDouble.round(x, 3, BigDecimal.ROUND_HALF_DOWN), 0.0);
		Assert.assertEquals(1.2346, RoundDouble.round(x, 4, BigDecimal.ROUND_HALF_DOWN), 0.0);
		Assert.assertEquals(-1.23, RoundDouble.round(-x, 2, BigDecimal.ROUND_HALF_DOWN), 0.0);
		Assert.assertEquals(-1.235, RoundDouble.round(-x, 3, BigDecimal.ROUND_HALF_DOWN), 0.0);
		Assert.assertEquals(-1.2346, RoundDouble.round(-x, 4, BigDecimal.ROUND_HALF_DOWN), 0.0);
		Assert.assertEquals(1.234, RoundDouble.round(1.2345, 3, BigDecimal.ROUND_HALF_DOWN), 0.0);
		Assert.assertEquals(-1.234, RoundDouble.round(-1.2345, 3, BigDecimal.ROUND_HALF_DOWN), 0.0);

		Assert.assertEquals(1.23, RoundDouble.round(x, 2, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(1.235, RoundDouble.round(x, 3, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(1.2346, RoundDouble.round(x, 4, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(-1.23, RoundDouble.round(-x, 2, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(-1.235, RoundDouble.round(-x, 3, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(-1.2346, RoundDouble.round(-x, 4, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(1.234, RoundDouble.round(1.2345, 3, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(-1.234, RoundDouble.round(-1.2345, 3, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(1.236, RoundDouble.round(1.2355, 3, BigDecimal.ROUND_HALF_EVEN), 0.0);
		Assert.assertEquals(-1.236, RoundDouble.round(-1.2355, 3, BigDecimal.ROUND_HALF_EVEN), 0.0);

		Assert.assertEquals(1.23, RoundDouble.round(x, 2, BigDecimal.ROUND_HALF_UP), 0.0);
		Assert.assertEquals(1.235, RoundDouble.round(x, 3, BigDecimal.ROUND_HALF_UP), 0.0);
		Assert.assertEquals(1.2346, RoundDouble.round(x, 4, BigDecimal.ROUND_HALF_UP), 0.0);
		Assert.assertEquals(-1.23, RoundDouble.round(-x, 2, BigDecimal.ROUND_HALF_UP), 0.0);
		Assert.assertEquals(-1.235, RoundDouble.round(-x, 3, BigDecimal.ROUND_HALF_UP), 0.0);
		Assert.assertEquals(-1.2346, RoundDouble.round(-x, 4, BigDecimal.ROUND_HALF_UP), 0.0);
		Assert.assertEquals(1.235, RoundDouble.round(1.2345, 3, BigDecimal.ROUND_HALF_UP), 0.0);
		Assert.assertEquals(-1.235, RoundDouble.round(-1.2345, 3, BigDecimal.ROUND_HALF_UP), 0.0);

		Assert.assertEquals(-1.23, RoundDouble.round(-1.23, 2, BigDecimal.ROUND_UNNECESSARY), 0.0);
		Assert.assertEquals(1.23, RoundDouble.round(1.23, 2, BigDecimal.ROUND_UNNECESSARY), 0.0);

		try {
			RoundDouble.round(1.234, 2, BigDecimal.ROUND_UNNECESSARY);
			Assert.fail();
		} catch (ArithmeticException ex) {
			// expected
		}

		Assert.assertEquals(1.24, RoundDouble.round(x, 2, BigDecimal.ROUND_UP), 0.0);
		Assert.assertEquals(1.235, RoundDouble.round(x, 3, BigDecimal.ROUND_UP), 0.0);
		Assert.assertEquals(1.2346, RoundDouble.round(x, 4, BigDecimal.ROUND_UP), 0.0);
		Assert.assertEquals(-1.24, RoundDouble.round(-x, 2, BigDecimal.ROUND_UP), 0.0);
		Assert.assertEquals(-1.235, RoundDouble.round(-x, 3, BigDecimal.ROUND_UP), 0.0);
		Assert.assertEquals(-1.2346, RoundDouble.round(-x, 4, BigDecimal.ROUND_UP), 0.0);

		try {
			RoundDouble.round(1.234, 2, 1923);
			Assert.fail();
		} catch (IllegalArgumentException ex) {
			// expected
		}

		// MATH-151
		Assert.assertEquals(39.25, RoundDouble.round(39.245, 2, BigDecimal.ROUND_HALF_UP), 0.0);

		// special values
		assertEquals(Double.NaN, RoundDouble.round(Double.NaN, 2), 0.0);
		Assert.assertEquals(0.0, RoundDouble.round(0.0, 2), 0.0);
		Assert.assertEquals(Double.POSITIVE_INFINITY, RoundDouble.round(Double.POSITIVE_INFINITY, 2), 0.0);
		Assert.assertEquals(Double.NEGATIVE_INFINITY, RoundDouble.round(Double.NEGATIVE_INFINITY, 2), 0.0);
		// comparison of positive and negative zero is not possible -> always
		// equal thus do string comparison
		Assert.assertEquals("-0.0", Double.toString(RoundDouble.round(-0.0, 0)));
		Assert.assertEquals("-0.0", Double.toString(RoundDouble.round(-1e-10, 0)));
	}

	/**
	 * Verifies that expected and actual are within delta, or are both NaN or
	 * infinities of the same sign.
	 */
	public static void assertEquals(double expected, double actual, double delta) {
		Assert.assertEquals(null, expected, actual, delta);
	}

}
