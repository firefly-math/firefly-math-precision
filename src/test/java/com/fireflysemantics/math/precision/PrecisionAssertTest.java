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

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for the {@link PrecisionAssertAssert} class.
 */

public class PrecisionAssertTest {

	@Test
	public void testEqualsWithRelativeTolerance() {
		Assert.assertTrue(PrecisionAssert.equalsWithRelativeTolerance(0d, 0d, 0d));
		Assert.assertTrue(PrecisionAssert.equalsWithRelativeTolerance(0d, 1
				/ Double.NEGATIVE_INFINITY, 0d));

		final double eps = 1e-14;
		Assert.assertFalse(
				PrecisionAssert.equalsWithRelativeTolerance(1.987654687654968, 1.987654687654988, eps));
		Assert.assertTrue(
				PrecisionAssert.equalsWithRelativeTolerance(1.987654687654968, 1.987654687654987, eps));
		Assert.assertFalse(
				PrecisionAssert.equalsWithRelativeTolerance(1.987654687654968, 1.987654687654948, eps));
		Assert.assertTrue(
				PrecisionAssert.equalsWithRelativeTolerance(1.987654687654968, 1.987654687654949, eps));

		Assert.assertFalse(PrecisionAssert.equalsWithRelativeTolerance(PrecisionAssert.SAFE_MIN, 0.0, eps));

		Assert.assertFalse(PrecisionAssert.equalsWithRelativeTolerance(1.0000000000001e-300, 1e-300, eps));
		Assert.assertTrue(PrecisionAssert.equalsWithRelativeTolerance(1.00000000000001e-300, 1e-300, eps));

		Assert.assertFalse(PrecisionAssert.equalsWithRelativeTolerance(Double.NEGATIVE_INFINITY, 1.23, eps));
		Assert.assertFalse(PrecisionAssert.equalsWithRelativeTolerance(Double.POSITIVE_INFINITY, 1.23, eps));

		Assert.assertTrue(PrecisionAssert.equalsWithRelativeTolerance(Double.NEGATIVE_INFINITY,
				Double.NEGATIVE_INFINITY, eps));
		Assert.assertTrue(PrecisionAssert.equalsWithRelativeTolerance(Double.POSITIVE_INFINITY,
				Double.POSITIVE_INFINITY, eps));
		Assert.assertFalse(PrecisionAssert.equalsWithRelativeTolerance(Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, eps));

		Assert.assertFalse(PrecisionAssert.equalsWithRelativeTolerance(Double.NaN, 1.23, eps));
		Assert.assertFalse(PrecisionAssert.equalsWithRelativeTolerance(Double.NaN, Double.NaN, eps));
	}

	@Test
	public void testEqualsIncludingNaN() {
		double[] testArray = { Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 1d, 0d };
		for (int i = 0; i < testArray.length; i++) {
			for (int j = 0; j < testArray.length; j++) {
				if (i == j) {
					Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(testArray[i], testArray[j]));
					Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(testArray[j], testArray[i]));
				} else {
					Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(testArray[i], testArray[j]));
					Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(testArray[j], testArray[i]));
				}
			}
		}
	}

	@Test
	public void testEqualsWithAllowedDelta() {
		Assert.assertTrue(PrecisionAssert.equals(153.0000, 153.0000, .0625));
		Assert.assertTrue(PrecisionAssert.equals(153.0000, 153.0625, .0625));
		Assert.assertTrue(PrecisionAssert.equals(152.9375, 153.0000, .0625));
		Assert.assertFalse(PrecisionAssert.equals(153.0000, 153.0625, .0624));
		Assert.assertFalse(PrecisionAssert.equals(152.9374, 153.0000, .0625));
		Assert.assertFalse(PrecisionAssert.equals(Double.NaN, Double.NaN, 1.0));
		Assert.assertTrue(PrecisionAssert.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
		Assert.assertTrue(PrecisionAssert.equals(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 1.0));
		Assert.assertFalse(PrecisionAssert.equals(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
	}

	/**
	 * See https://issues.apache.org/jira/browse/MATH-475
	 */
	@Test
	public void testMath475() {
		final double a = 1.7976931348623182E16;
		final double b = Math.nextUp(a);

		double diff = Math.abs(a
				- b);
		// Because they are adjacent floating point numbers, "a" and "b" are
		// considered equal even though the allowed error is smaller than
		// their difference.
		Assert.assertTrue(PrecisionAssert.equals(a, b, 0.5
				* diff));

		final double c = Math.nextUp(b);
		diff = Math.abs(a
				- c);
		// Because "a" and "c" are not adjacent, the tolerance is taken into
		// account for assessing equality.
		Assert.assertTrue(PrecisionAssert.equals(a, c, diff));
		Assert.assertFalse(PrecisionAssert.equals(a, c, (1
				- 1e-16)
				* diff));
	}

	@Test
	public void testEqualsIncludingNaNWithAllowedDelta() {
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(153.0000, 153.0000, .0625));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(153.0000, 153.0625, .0625));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(152.9375, 153.0000, .0625));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(Double.NaN, Double.NaN, 1.0));
		Assert.assertTrue(
				PrecisionAssert.equalsIncludingNaN(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
		Assert.assertTrue(
				PrecisionAssert.equalsIncludingNaN(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 1.0));
		Assert.assertFalse(
				PrecisionAssert.equalsIncludingNaN(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1.0));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(153.0000, 153.0625, .0624));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(152.9374, 153.0000, .0625));
	}

	@Test
	public void testEqualsWithAllowedUlps() {
		Assert.assertTrue(PrecisionAssert.equals(0.0, -0.0, 1));

		Assert.assertTrue(PrecisionAssert.equals(1.0, 1
				+ Math.ulp(1d), 1));
		Assert.assertFalse(PrecisionAssert.equals(1.0, 1
				+ 2
						* Math.ulp(1d),
				1));

		final double nUp1 = Math.nextAfter(1d, Double.POSITIVE_INFINITY);
		final double nnUp1 = Math.nextAfter(nUp1, Double.POSITIVE_INFINITY);
		Assert.assertTrue(PrecisionAssert.equals(1.0, nUp1, 1));
		Assert.assertTrue(PrecisionAssert.equals(nUp1, nnUp1, 1));
		Assert.assertFalse(PrecisionAssert.equals(1.0, nnUp1, 1));

		Assert.assertTrue(PrecisionAssert.equals(0.0, Math.ulp(0d), 1));
		Assert.assertTrue(PrecisionAssert.equals(0.0, -Math.ulp(0d), 1));

		Assert.assertTrue(PrecisionAssert.equals(153.0, 153.0, 1));

		Assert.assertTrue(PrecisionAssert.equals(153.0, 153.00000000000003, 1));
		Assert.assertFalse(PrecisionAssert.equals(153.0, 153.00000000000006, 1));
		Assert.assertTrue(PrecisionAssert.equals(153.0, 152.99999999999997, 1));
		Assert.assertFalse(PrecisionAssert.equals(153, 152.99999999999994, 1));

		Assert.assertTrue(PrecisionAssert.equals(-128.0, -127.99999999999999, 1));
		Assert.assertFalse(PrecisionAssert.equals(-128.0, -127.99999999999997, 1));
		Assert.assertTrue(PrecisionAssert.equals(-128.0, -128.00000000000003, 1));
		Assert.assertFalse(PrecisionAssert.equals(-128.0, -128.00000000000006, 1));

		Assert.assertTrue(PrecisionAssert.equals(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1));
		Assert.assertTrue(PrecisionAssert.equals(Double.MAX_VALUE, Double.POSITIVE_INFINITY, 1));

		Assert.assertTrue(PrecisionAssert.equals(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 1));
		Assert.assertTrue(PrecisionAssert.equals(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY, 1));

		Assert.assertFalse(PrecisionAssert.equals(Double.NaN, Double.NaN, 1));
		Assert.assertFalse(PrecisionAssert.equals(Double.NaN, Double.NaN, 0));
		Assert.assertFalse(PrecisionAssert.equals(Double.NaN, 0, 0));
		Assert.assertFalse(PrecisionAssert.equals(Double.NaN, Double.POSITIVE_INFINITY, 0));
		Assert.assertFalse(PrecisionAssert.equals(Double.NaN, Double.NEGATIVE_INFINITY, 0));

		Assert.assertFalse(
				PrecisionAssert.equals(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 100000));
	}

	@Test
	public void testEqualsIncludingNaNWithAllowedUlps() {
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(0.0, -0.0, 1));

		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(1.0, 1
				+ Math.ulp(1d), 1));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(1.0, 1
				+ 2
						* Math.ulp(1d),
				1));

		final double nUp1 = Math.nextAfter(1d, Double.POSITIVE_INFINITY);
		final double nnUp1 = Math.nextAfter(nUp1, Double.POSITIVE_INFINITY);
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(1.0, nUp1, 1));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(nUp1, nnUp1, 1));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(1.0, nnUp1, 1));

		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(0.0, Math.ulp(0d), 1));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(0.0, -Math.ulp(0d), 1));

		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(153.0, 153.0, 1));

		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(153.0, 153.00000000000003, 1));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(153.0, 153.00000000000006, 1));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(153.0, 152.99999999999997, 1));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(153, 152.99999999999994, 1));

		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(-128.0, -127.99999999999999, 1));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(-128.0, -127.99999999999997, 1));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(-128.0, -128.00000000000003, 1));
		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(-128.0, -128.00000000000006, 1));

		Assert.assertTrue(
				PrecisionAssert.equalsIncludingNaN(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 1));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(Double.MAX_VALUE, Double.POSITIVE_INFINITY, 1));

		Assert.assertTrue(
				PrecisionAssert.equalsIncludingNaN(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 1));
		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(-Double.MAX_VALUE, Double.NEGATIVE_INFINITY, 1));

		Assert.assertTrue(PrecisionAssert.equalsIncludingNaN(Double.NaN, Double.NaN, 1));

		Assert.assertFalse(PrecisionAssert.equalsIncludingNaN(Double.NEGATIVE_INFINITY,
				Double.POSITIVE_INFINITY, 100000));
	}

	@Test
	public void testCompareToEpsilon() {
		Assert.assertEquals(0, PrecisionAssert.compareTo(152.33, 152.32, .011));
		Assert.assertTrue(PrecisionAssert.compareTo(152.308, 152.32, .011) < 0);
		Assert.assertTrue(PrecisionAssert.compareTo(152.33, 152.318, .011) > 0);
		Assert.assertEquals(0, PrecisionAssert.compareTo(Double.MIN_VALUE, +0.0, Double.MIN_VALUE));
		Assert.assertEquals(0, PrecisionAssert.compareTo(Double.MIN_VALUE, -0.0, Double.MIN_VALUE));
	}

	@Test
	public void testCompareToMaxUlps() {
		double a = 152.32;
		double delta = Math.ulp(a);
		for (int i = 0; i <= 10; ++i) {
			if (i <= 5) {
				Assert.assertEquals(0, PrecisionAssert.compareTo(a, a
						+ i
								* delta,
						5));
				Assert.assertEquals(0, PrecisionAssert.compareTo(a, a
						- i
								* delta,
						5));
			} else {
				Assert.assertEquals(-1, PrecisionAssert.compareTo(a, a
						+ i
								* delta,
						5));
				Assert.assertEquals(+1, PrecisionAssert.compareTo(a, a
						- i
								* delta,
						5));
			}
		}

		Assert.assertEquals(0, PrecisionAssert.compareTo(-0.0, 0.0, 0));

		Assert.assertEquals(-1, PrecisionAssert.compareTo(-Double.MIN_VALUE, -0.0, 0));
		Assert.assertEquals(0, PrecisionAssert.compareTo(-Double.MIN_VALUE, -0.0, 1));
		Assert.assertEquals(-1, PrecisionAssert.compareTo(-Double.MIN_VALUE, +0.0, 0));
		Assert.assertEquals(0, PrecisionAssert.compareTo(-Double.MIN_VALUE, +0.0, 1));

		Assert.assertEquals(+1, PrecisionAssert.compareTo(Double.MIN_VALUE, -0.0, 0));
		Assert.assertEquals(0, PrecisionAssert.compareTo(Double.MIN_VALUE, -0.0, 1));
		Assert.assertEquals(+1, PrecisionAssert.compareTo(Double.MIN_VALUE, +0.0, 0));
		Assert.assertEquals(0, PrecisionAssert.compareTo(Double.MIN_VALUE, +0.0, 1));

		Assert.assertEquals(-1, PrecisionAssert.compareTo(-Double.MIN_VALUE, Double.MIN_VALUE, 0));
		Assert.assertEquals(-1, PrecisionAssert.compareTo(-Double.MIN_VALUE, Double.MIN_VALUE, 1));
		Assert.assertEquals(0, PrecisionAssert.compareTo(-Double.MIN_VALUE, Double.MIN_VALUE, 2));

		Assert.assertEquals(0, PrecisionAssert.compareTo(Double.MAX_VALUE, Double.POSITIVE_INFINITY, 1));
		Assert.assertEquals(-1, PrecisionAssert.compareTo(Double.MAX_VALUE, Double.POSITIVE_INFINITY, 0));

		Assert.assertEquals(+1, PrecisionAssert.compareTo(Double.MAX_VALUE, Double.NaN, Integer.MAX_VALUE));
		Assert.assertEquals(+1, PrecisionAssert.compareTo(Double.NaN, Double.MAX_VALUE, Integer.MAX_VALUE));
	}

	@Test
	public void testIssue721() {
		Assert.assertEquals(-53, Math.getExponent(PrecisionAssert.EPSILON));
		Assert.assertEquals(-1022, Math.getExponent(PrecisionAssert.SAFE_MIN));
	}

	@Test
	public void testRepresentableDelta() {
		int nonRepresentableCount = 0;
		final double x = 100;
		final int numTrials = 10000;
		for (int i = 0; i < numTrials; i++) {
			final double originalDelta = Math.random();
			final double delta = PrecisionAssert.representableDelta(x, originalDelta);
			if (delta != originalDelta) {
				++nonRepresentableCount;
			}
		}

		Assert.assertTrue(nonRepresentableCount
				/ (double) numTrials > 0.9);
	}

	@Test
	public void testMath843() {
		final double afterEpsilon = Math.nextAfter(PrecisionAssert.EPSILON, Double.POSITIVE_INFINITY);

		// a) 1 + EPSILON is equal to 1.
		Assert.assertTrue(1
				+ PrecisionAssert.EPSILON == 1);

		// b) 1 + "the number after EPSILON" is not equal to 1.
		Assert.assertFalse(1
				+ afterEpsilon == 1);
	}

	@Test
	public void testMath1127() {
		Assert.assertFalse(PrecisionAssert.equals(2.0, -2.0, 1));
		Assert.assertTrue(PrecisionAssert.equals(0.0, -0.0, 0));
		Assert.assertFalse(PrecisionAssert.equals(2.0f, -2.0f, 1));
		Assert.assertTrue(PrecisionAssert.equals(0.0f, -0.0f, 0));
	}

}
