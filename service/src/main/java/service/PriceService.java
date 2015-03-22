package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.RealDistribution;

import Pojo.UserBidRequest;
import Pojo.PricePoint;

public class PriceService {

	private static final PriceService instance = new PriceService();

	public static final PriceService getInstance() {
		return instance;
	}

	private PriceService() {

	}

	public double getNormalPrice(UserBidRequest request) {
		double base = request.getStar() * 200;
		switch (request.getType()) {
		case "商务型":
			return base * 1.2;
		case "2":
			return base * 1.2;
		case "经济型":
			return base * 0.8;
		case "1":
			return base * 0.8;
		case "度假型":
			return base;
		case "3":
			return base;
		default:
			return base;
		}
	}

	public List<PricePoint> getPricePoint(double normalPrice, int timeout, Date currentDate) {
		RealDistribution distribution = getDistribution(timeout, currentDate);
		List<PricePoint> result = new ArrayList<PricePoint>();
		if (distribution instanceof NormalDistribution) {
			double[] xAxis = new double[] { -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0 };
			for (double x : xAxis) {
				result.add(new PricePoint(Math.round(normalPrice * (1 + x)), distribution.cumulativeProbability(x) * 2));
			}
		} else if (distribution instanceof ExponentialDistribution) {
			double[] xAxis = new double[] { -0.9, -0.8, -0.7, -0.6, -0.5, -0.4, -0.3, -0.2, -0.1, 0 };
			for (double x : xAxis) {
				result.add(new PricePoint(Math.round(normalPrice * (1 + x)), 1 - distribution.cumulativeProbability(-x)));
			}
		}
		return result;
	}

	/**
	 * 
	 * @param timeout
	 *           in minutes
	 * @param currentDate
	 * @return
	 */
	private RealDistribution getDistribution(int timeout, Date currentDate) {
		DateRange range = getDateRange(currentDate);
		switch (range) {
		case MIDNIGHT:
			if (timeout <= 5) {

			} else {
				return new NormalDistribution(0, 1);
			}
		case MORNING:
			if (timeout <= 5) {
				return new ExponentialDistribution(1);
			} else if (timeout <= 60) {
				return new NormalDistribution(0, 2);
			} else {
				return new NormalDistribution(0, 3);
			}
		case AFTERNOON:
			if (timeout <= 5) {
				return new ExponentialDistribution(1);
			} else if (timeout <= 60) {
				return new NormalDistribution(0, 2);
			} else {
				return new NormalDistribution(0, 3);
			}
		case EVENING:
			if (timeout <= 5) {
				return new ExponentialDistribution(1);
			} else if (timeout <= 60) {
				return new NormalDistribution(0, 2);
			} else {
				return new NormalDistribution(0, 3);
			}
		default:
			return new NormalDistribution();
		}
	}

	private DateRange getDateRange(Date currentDate) {
		if (currentDate.getHours() >= 0 && currentDate.getHours() < 6) {
			return DateRange.MIDNIGHT;
		} else if (currentDate.getHours() >= 6 && currentDate.getHours() < 12) {
			return DateRange.MORNING;
		} else if (currentDate.getHours() >= 12 && currentDate.getHours() < 18) {
			return DateRange.AFTERNOON;
		} else if (currentDate.getHours() >= 18 && currentDate.getHours() < 22) {
			return DateRange.EVENING;
		} else {
			return DateRange.MIDNIGHT;
		}
	}

	enum DateRange {
		MORNING, AFTERNOON, EVENING, MIDNIGHT
	}
}
