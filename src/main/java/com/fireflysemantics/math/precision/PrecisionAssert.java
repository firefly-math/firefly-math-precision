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
public class PrecisionAssert {
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

	/** Offset to order signed double numbers lexicographically. */
	private static final long SGN_MASK = 0x8000000000000000L;
	/** Positive zero bits. */
	private static final long POSITIVE_ZERO_DOUBLE_BITS = Double.doubleToRawLongBits(+0.0);
	/** Negative zero bits. */
	private static final long NEGATIVE_ZERO_DOUBLE_BITS = Double.doubleToRawLongBits(-0.0);

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
	private PrecisionAssert() {
	}

	/**
	 * Compares two numbers given some amount of allowed error.
	 *
	 * @param x
	 *            the first number
	 * @param y
	 *            the second number
	 * @param eps
	 *            the amount of error to allow when checking for equality
	 * @return
	 * 		<ul>
	 *         <li>0 if {@link #equals(double, double, double) equals(x, y,
	 *         eps)}</li>
	 *         <li>&lt; 0 if !{@link #equals(double, double, double) equals(x,
	 *         y, eps)} &amp;&amp; x &lt; y</li>
	 *         <li>> 0 if !{@link #equals(double, double, double) equals(x, y,
	 *         eps)} &amp;&amp; x > y</li>
	 *         </ul>
	 */
	public static int compareTo(double x, double y, double eps) {
		if (equals(x, y, eps)) {
			return 0;
		} else if (x < y) {
			return -1;
		}
		return 1;
	}

	/**
	 * Compares two numbers given some amount of allowed error. Two float
	 * numbers are considered equal if there are {@code (maxUlps - 1)} (or
	 * fewer) floating point numbers between them, i.e. two adjacent floating
	 * point numbers are considered equal. Adapted from <a href=
	 * "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/">
	 * Bruce Dawson</a>
	 *
	 * @param x
	 *            first value
	 * @param y
	 *            second value
	 * @param maxUlps
	 *            {@code (maxUlps - 1)} is the number of floating point values
	 *            between {@code x} and {@code y}.
	 * @return
	 * 		<ul>
	 *         <li>0 if {@link #equals(double, double, int) equals(x, y,
	 *         maxUlps)}</li>
	 *         <li>&lt; 0 if !{@link #equals(double, double, int) equals(x, y,
	 *         maxUlps)} &amp;&amp; x &lt; y</li>
	 *         <li>> 0 if !{@link #equals(double, double, int) equals(x, y,
	 *         maxUlps)} &amp;&amp; x > y</li>
	 *         </ul>
	 */
	public static int compareTo(final double x, final double y, final int maxUlps) {
		if (equals(x, y, maxUlps)) {
			return 0;
		} else if (x < y) {
			return -1;
		}
		return 1;
	}

	/**
	 * Returns true iff they are equal as defined by
	 * {@link #equals(double,double,int) equals(x, y, 1)}.
	 *
	 * @param x
	 *            first value
	 * @param y
	 *            second value
	 * @return {@code true} if the values are equal.
	 */
	public static boolean equals(double x, double y) {
		return equals(x, y, 1);
	}

	/**
	 * Returns true if both arguments are NaN or neither is NaN and they are
	 * equal as defined by {@link #equals(double,double) equals(x, y, 1)}.
	 *
	 * @param x
	 *            first value
	 * @param y
	 *            second value
	 * @return {@code true} if the values are equal or both are NaN.
	 */
	public static boolean equalsIncludingNaN(double x, double y) {
		return (x != x
				|| y != y)
						? !(x != x
								^ y != y)
						: equals(x, y, 1);
	}

	/**
	 * Returns {@code true} if there is no double value strictly between the
	 * arguments or the difference between them is within the range of allowed
	 * error (inclusive).
	 *
	 * @param x
	 *            First value.
	 * @param y
	 *            Second value.
	 * @param eps
	 *            Amount of allowed absolute error.
	 * @return {@code true} if the values are two adjacent floating point
	 *         numbers or they are within range of each other.
	 */
	public static boolean equals(double x, double y, double eps) {
		return equals(x, y, 1)
				|| Math.abs(y
						- x) <= eps;
	}

	/**
	 * Returns {@code true} if there is no double value strictly between the
	 * arguments or the relative difference between them is smaller or equal to
	 * the given tolerance.
	 *
	 * @param x
	 *            First value.
	 * @param y
	 *            Second value.
	 * @param eps
	 *            Amount of allowed relative error.
	 * @return {@code true} if the values are two adjacent floating point
	 *         numbers or they are within range of each other.
	 * @since 3.1
	 */
	public static boolean equalsWithRelativeTolerance(double x, double y, double eps) {
		if (equals(x, y, 1)) {
			return true;
		}

		final double absoluteMax = Math.max(Math.abs(x), Math.abs(y));
		final double relativeDifference = Math.abs((x
				- y)
				/ absoluteMax);

		return relativeDifference <= eps;
	}

	/**
	 * Returns true if both arguments are NaN or are equal or within the range
	 * of allowed error (inclusive).
	 *
	 * @param x
	 *            first value
	 * @param y
	 *            second value
	 * @param eps
	 *            the amount of absolute error to allow.
	 * @return {@code true} if the values are equal or within range of each
	 *         other, or both are NaN.
	 * @since 2.2
	 */
	public static boolean equalsIncludingNaN(double x, double y, double eps) {
		return equalsIncludingNaN(x, y)
				|| (Math.abs(y
						- x) <= eps);
	}

	/**
	 * Returns true if both arguments are equal or within the range of allowed
	 * error (inclusive).
	 * <p>
	 * Two float numbers are considered equal if there are {@code (maxUlps - 1)}
	 * (or fewer) floating point numbers between them, i.e. two adjacent
	 * floating point numbers are considered equal.
	 * </p>
	 * <p>
	 * Adapted from <a href=
	 * "http://randomascii.wordpress.com/2012/02/25/comparing-floating-point-numbers-2012-edition/">
	 * Bruce Dawson</a>
	 * </p>
	 *
	 * @param x
	 *            first value
	 * @param y
	 *            second value
	 * @param maxUlps
	 *            {@code (maxUlps - 1)} is the number of floating point values
	 *            between {@code x} and {@code y}.
	 * @return {@code true} if there are fewer than {@code maxUlps} floating
	 *         point values between {@code x} and {@code y}.
	 */
	public static boolean equals(final double x, final double y, final int maxUlps) {

		final long xInt = Double.doubleToRawLongBits(x);
		final long yInt = Double.doubleToRawLongBits(y);

		final boolean isEqual;
		if (((xInt
				^ yInt)
				& SGN_MASK) == 0l) {
			// number have same sign, there is no risk of overflow
			isEqual = Math.abs(xInt
					- yInt) <= maxUlps;
		} else {
			// number have opposite signs, take care of overflow
			final long deltaPlus;
			final long deltaMinus;
			if (xInt < yInt) {
				deltaPlus = yInt
						- POSITIVE_ZERO_DOUBLE_BITS;
				deltaMinus = xInt
						- NEGATIVE_ZERO_DOUBLE_BITS;
			} else {
				deltaPlus = xInt
						- POSITIVE_ZERO_DOUBLE_BITS;
				deltaMinus = yInt
						- NEGATIVE_ZERO_DOUBLE_BITS;
			}

			if (deltaPlus > maxUlps) {
				isEqual = false;
			} else {
				isEqual = deltaMinus <= (maxUlps
						- deltaPlus);
			}

		}

		return isEqual
				&& !Double.isNaN(x)
				&& !Double.isNaN(y);

	}

	/**
	 * Returns true if both arguments are NaN or if they are equal as defined by
	 * {@link #equals(double,double,int) equals(x, y, maxUlps)}.
	 *
	 * @param x
	 *            first value
	 * @param y
	 *            second value
	 * @param maxUlps
	 *            {@code (maxUlps - 1)} is the number of floating point values
	 *            between {@code x} and {@code y}.
	 * @return {@code true} if both arguments are NaN or if there are less than
	 *         {@code maxUlps} floating point values between {@code x} and
	 *         {@code y}.
	 * @since 2.2
	 */
	public static boolean equalsIncludingNaN(double x, double y, int maxUlps) {
		return (x != x
				|| y != y)
						? !(x != x
								^ y != y)
						: equals(x, y, maxUlps);
	}

	/**
	 * Computes a number {@code delta} close to {@code originalDelta} with the
	 * property that
	 * 
	 * <pre>
	 * <code>
	 *   x + delta - x
	 * </code>
	 * </pre>
	 * 
	 * is exactly machine-representable. This is useful when computing numerical
	 * derivatives, in order to reduce roundoff errors.
	 *
	 * @param x
	 *            Value.
	 * @param originalDelta
	 *            Offset value.
	 * @return a number {@code delta} so that {@code x + delta} and {@code x}
	 *         differ by a representable floating number.
	 */
	public static double representableDelta(double x, double originalDelta) {
		return x
				+ originalDelta
				- x;
	}
}
