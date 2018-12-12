package com.yeegot.map.track;

import com.baidu.mapapi.model.LatLng;

public class TrackUtils {


    public static double calXMoveDistance(LatLng startPoint, LatLng endPoint, double DISTANCE, double length) {
        return Math.abs((endPoint.latitude - startPoint.latitude) / length * DISTANCE);
    }

    public static double calYMoveDistance(LatLng startPoint, LatLng endPoint, double DISTANCE, double length) {
        return Math.abs((endPoint.longitude - startPoint.longitude) / length * DISTANCE);
    }

    public static double getLength(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        return Math.sqrt(Math.pow(Math.abs(toPoint.latitude - fromPoint.latitude), 2) + Math.pow(Math.abs(toPoint.longitude - fromPoint.longitude), 2));
    }

    /**
     * 根据两点算取图标转的角度
     */
    public static double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 90;
        return angle;
    }

    /**
     * 算斜率
     * 结束的维度减去开始的维度（横） / 结束的经度减去开始的经度（竖）
     * x / y
     */
    public static double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;
    }


}
