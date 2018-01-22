///*
// * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
// */
//package com.ninetop.activity.ub.product;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.InfoWindow;
//import com.baidu.mapapi.map.MapPoi;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.map.MyLocationConfiguration;
//import com.baidu.mapapi.map.MyLocationData;
//import com.baidu.mapapi.model.LatLng;
//import com.baidu.mapapi.search.core.RouteLine;
//import com.baidu.mapapi.search.core.SearchResult;
//import com.baidu.mapapi.search.geocode.GeoCodeOption;
//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
//import com.baidu.mapapi.search.route.BikingRouteLine;
//import com.baidu.mapapi.search.route.BikingRoutePlanOption;
//import com.baidu.mapapi.search.route.BikingRouteResult;
//import com.baidu.mapapi.search.route.DrivingRouteLine;
//import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
//import com.baidu.mapapi.search.route.DrivingRouteResult;
//import com.baidu.mapapi.search.route.IndoorRouteResult;
//import com.baidu.mapapi.search.route.MassTransitRouteLine;
//import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
//import com.baidu.mapapi.search.route.MassTransitRouteResult;
//import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
//import com.baidu.mapapi.search.route.PlanNode;
//import com.baidu.mapapi.search.route.RoutePlanSearch;
//import com.baidu.mapapi.search.route.TransitRouteLine;
//import com.baidu.mapapi.search.route.TransitRoutePlanOption;
//import com.baidu.mapapi.search.route.TransitRouteResult;
//import com.baidu.mapapi.search.route.WalkingRouteLine;
//import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
//import com.baidu.mapapi.search.route.WalkingRouteResult;
//import com.ninetop.adatper.product.RouteLineAdapter;
//import com.ninetop.common.IntentExtraKeyConst;
//import com.ninetop.common.constant.SharedKeyConstant;
//import com.ninetop.common.util.MySharedPreference;
//import com.ninetop.common.util.ToastUtils;
//
//import java.util.List;
//
//import mapapi.overlayutil.BikingRouteOverlay;
//import mapapi.overlayutil.DrivingRouteOverlay;
//import mapapi.overlayutil.MassTransitRouteOverlay;
//import mapapi.overlayutil.OverlayManager;
//import mapapi.overlayutil.TransitRouteOverlay;
//import mapapi.overlayutil.WalkingRouteOverlay;
//import youbao.shopping.R;
//
///**
// * 此demo用来展示如何进行驾车、步行、公交、骑行、跨城综合路线搜索并在地图使用RouteOverlay、TransitOverlay绘制
// * 同时展示如何进行节点浏览并弹出泡泡
// */
//public class RoutePlanActivity extends Activity implements BaiduMap.OnMapClickListener,
//        OnGetRoutePlanResultListener {
//    // 浏览路线节点相关
//    Button mBtnPre = null; // 上一个节点
//    Button mBtnNext = null; // 下一个节点
//    int nodeIndex = -1; // 节点索引,供浏览节点时使用
//    RouteLine route = null;
//    MassTransitRouteLine massRoute = null;
//    OverlayManager routeOverlay = null;
//    boolean useDefaultIcon = false;
//
//    // 地图相关，使用继承MapView的MyRouteMapView目的是重写touch事件实现泡泡处理
//    // 如果不处理touch事件，则无需继承，直接使用MapView即可
//    MapView mMapView = null;    // 地图View
//    BaiduMap mBaiDuMap = null;
//    // 搜索相关
//    RoutePlanSearch mSearch = null;    // 搜索模块，也可去掉地图模块独立使用
//
//    WalkingRouteResult nowResultWalk = null;
//    BikingRouteResult nowResultBike = null;
//    TransitRouteResult nowResultTransit = null;
//    DrivingRouteResult nowResultDrive = null;
//    MassTransitRouteResult nowResultMass = null;
//
//    int nowSearchType = -1; // 当前进行的检索，供判断浏览节点时结果使用。
//
//    String startNodeStr = "";
//    String endNodeStr = "";
//    boolean hasShownDialogue = false;
//    GeoCoder mGeoCoderSearch;
//    String mCityBuyer;
//    String mStreetBuyer;
//    String mCitySelf;
//    String mAddressDetail;
//    String address;
//    LatLng endLatLng;
//    LatLng startLatLng;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_routeplan);
//        initView();
//        initData();
//    }
//
//    private void initView() {
//        // 初始化地图
//        mMapView = (MapView) findViewById(R.id.map);
//        mBaiDuMap = mMapView.getMap();
//        mBtnPre = (Button) findViewById(R.id.pre);
//        mBtnNext = (Button) findViewById(R.id.next);
//        mBtnPre.setVisibility(View.INVISIBLE);
//        mBtnNext.setVisibility(View.INVISIBLE);
//        // 地图点击事件处理
//        mBaiDuMap.setOnMapClickListener(this);
//        // 初始化路径规划搜索模块，注册事件监听
//        mSearch = RoutePlanSearch.newInstance();
//        mSearch.setOnGetRoutePlanResultListener(this);
//
//        // 初始化搜索模块，注册事件监听
//        mGeoCoderSearch = GeoCoder.newInstance();
//        mGeoCoderSearch.setOnGetGeoCodeResultListener(listener);
//
//        //开启定位图层
//        mBaiDuMap.setMyLocationEnabled(true);
//    }
//
//    private void initData() {
//        //店家地址
//        Intent mIntent = getIntent();
//        mAddressDetail = mIntent.getStringExtra(IntentExtraKeyConst.ADDRESS_DETAIL);
//        int mLength = mAddressDetail.length();
//        mCityBuyer = mAddressDetail.substring(3, 6);
//        endNodeStr = mStreetBuyer;
//        mStreetBuyer = mAddressDetail.substring(6, mLength);
//        mGeoCoderSearch.geocode(new GeoCodeOption().city(
//                mCityBuyer).address(mStreetBuyer));
//        //当前位置
//        String latitude = MySharedPreference.get(SharedKeyConstant.LATITUDE, "", this);
//        String longitude = MySharedPreference.get(SharedKeyConstant.LONGITUDE, "", this);
//
//        // 构造定位数据
//        MyLocationData locData = new MyLocationData.Builder()
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(100).latitude(Double.valueOf(latitude))
//                .longitude(Double.valueOf(longitude)).build();
//        // 设置定位数据
//        mBaiDuMap.setMyLocationData(locData);
//        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
//                .fromResource(R.drawable.icon_st);
//        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
//        mBaiDuMap.setMyLocationConfiguration(config);
//        address = MySharedPreference.get(SharedKeyConstant.ADDRESS, "", this);
//        if ("".equals(address)) {
//            return;
//        }
//        startNodeStr = address.substring(8, address.length());
//        mCitySelf = MySharedPreference.get(SharedKeyConstant.CITY_NAME, "", this);
//    }
//
//    /**
//     * 发起路线规划搜索示
//     */
//    public void searchButtonProcess(View v) {
//        // 重置浏览节点的路线数据
//        route = null;
//        mBtnPre.setVisibility(View.INVISIBLE);
//        mBtnNext.setVisibility(View.INVISIBLE);
//        mBaiDuMap.clear();
//        // 处理搜索按钮响应
//        // 设置起终点信息，对于transit search 来说，城市名无意义
//        String latitude = MySharedPreference.get(SharedKeyConstant.LATITUDE, "", this);
//        String longitude = MySharedPreference.get(SharedKeyConstant.LONGITUDE, "", this);
//        startLatLng = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
//        PlanNode stNode = PlanNode.withLocation(startLatLng);
//        PlanNode enNode = PlanNode.withLocation(endLatLng);
//        // 实际使用中请对起点终点城市进行正确的设定
//        if (v.getId() == R.id.mass) {
//            mSearch.masstransitSearch(new MassTransitRoutePlanOption().from(stNode).to(enNode));
//            nowSearchType = 0;
//        } else if (v.getId() == R.id.drive) {
//            mSearch.drivingSearch((new DrivingRoutePlanOption())
//                    .from(stNode).to(enNode));
//            nowSearchType = 1;
//        } else if (v.getId() == R.id.transit) {
//            mSearch.transitSearch((new TransitRoutePlanOption())
//                    .from(stNode).city(mCitySelf).to(enNode));
//            nowSearchType = 2;
//        } else if (v.getId() == R.id.walk) {
//            mSearch.walkingSearch((new WalkingRoutePlanOption())
//                    .from(stNode).to(enNode));
//            nowSearchType = 3;
//        } else if (v.getId() == R.id.bike) {
//            mSearch.bikingSearch((new BikingRoutePlanOption())
//                    .from(stNode).to(enNode));
//            nowSearchType = 4;
//        }
//    }
//
//    /**
//     * 节点浏览示例
//     */
//    public void nodeClick(View v) {
//        LatLng nodeLocation = null;
//        String nodeTitle = null;
//        Object step = null;
//
//        if (nowSearchType != 0 && nowSearchType != -1) {
//            // 非跨城综合交通
//            if (route == null || route.getAllStep() == null) {
//                return;
//            }
//            if (nodeIndex == -1 && v.getId() == R.id.pre) {
//                return;
//            }
//            // 设置节点索引
//            if (v.getId() == R.id.next) {
//                if (nodeIndex < route.getAllStep().size() - 1) {
//                    nodeIndex++;
//                } else {
//                    return;
//                }
//            } else if (v.getId() == R.id.pre) {
//                if (nodeIndex > 0) {
//                    nodeIndex--;
//                } else {
//                    return;
//                }
//            }
//            // 获取节结果信息
//            step = route.getAllStep().get(nodeIndex);
//            if (step instanceof DrivingRouteLine.DrivingStep) {
//                nodeLocation = ((DrivingRouteLine.DrivingStep) step).getEntrance().getLocation();
//                nodeTitle = ((DrivingRouteLine.DrivingStep) step).getInstructions();
//            } else if (step instanceof WalkingRouteLine.WalkingStep) {
//                nodeLocation = ((WalkingRouteLine.WalkingStep) step).getEntrance().getLocation();
//                nodeTitle = ((WalkingRouteLine.WalkingStep) step).getInstructions();
//            } else if (step instanceof TransitRouteLine.TransitStep) {
//                nodeLocation = ((TransitRouteLine.TransitStep) step).getEntrance().getLocation();
//                nodeTitle = ((TransitRouteLine.TransitStep) step).getInstructions();
//            } else if (step instanceof BikingRouteLine.BikingStep) {
//                nodeLocation = ((BikingRouteLine.BikingStep) step).getEntrance().getLocation();
//                nodeTitle = ((BikingRouteLine.BikingStep) step).getInstructions();
//            }
//        } else if (nowSearchType == 0) {
//            // 跨城综合交通  综合跨城公交的结果判断方式不一样
//
//            if (massRoute == null || massRoute.getNewSteps() == null) {
//                return;
//            }
//            if (nodeIndex == -1 && v.getId() == R.id.pre) {
//                return;
//            }
//            boolean isSameCity = nowResultMass.getOrigin().getCityId() == nowResultMass.getDestination().getCityId();
//            int size = 0;
//            if (isSameCity) {
//                size = massRoute.getNewSteps().size();
//            } else {
//                for (int i = 0; i < massRoute.getNewSteps().size(); i++) {
//                    size += massRoute.getNewSteps().get(i).size();
//                }
//            }
//
//            // 设置节点索引
//            if (v.getId() == R.id.next) {
//                if (nodeIndex < size - 1) {
//                    nodeIndex++;
//                } else {
//                    return;
//                }
//            } else if (v.getId() == R.id.pre) {
//                if (nodeIndex > 0) {
//                    nodeIndex--;
//                } else {
//                    return;
//                }
//            }
//            if (isSameCity) {
//                // 同城
//                step = massRoute.getNewSteps().get(nodeIndex).get(0);
//            } else {
//                // 跨城
//                int num = 0;
//                for (int j = 0; j < massRoute.getNewSteps().size(); j++) {
//                    num += massRoute.getNewSteps().get(j).size();
//                    if (nodeIndex - num < 0) {
//                        int k = massRoute.getNewSteps().get(j).size() + nodeIndex - num;
//                        step = massRoute.getNewSteps().get(j).get(k);
//                        break;
//                    }
//                }
//            }
//
//            if (step != null) {
//                nodeLocation = ((MassTransitRouteLine.TransitStep) step).getStartLocation();
//            }
//            assert step != null;
//            nodeTitle = ((MassTransitRouteLine.TransitStep) step).getInstructions();
//        }
//
//        if (nodeLocation == null || nodeTitle == null) {
//            return;
//        }
//
//        // 移动节点至中心
//        mBaiDuMap.setMapStatus(MapStatusUpdateFactory.newLatLng(nodeLocation));
//        // show popup
//        TextView popupText = new TextView(RoutePlanActivity.this);
//        popupText.setBackgroundResource(R.drawable.popup);
//        popupText.setTextColor(0xFF000000);
//        popupText.setText(nodeTitle);
//        mBaiDuMap.showInfoWindow(new InfoWindow(popupText, nodeLocation, 0));
//    }
//
//    /**
//     * 切换路线图标，刷新地图使其生效
//     * 注意： 起终点图标使用中心对齐.
//     */
//    public void changeRouteIcon(View v) {
//        if (routeOverlay == null) {
//            return;
//        }
//        if (useDefaultIcon) {
//            ((Button) v).setText("自定义起终点图标");
//            Toast.makeText(this,
//                    "将使用系统起终点图标",
//                    Toast.LENGTH_SHORT).show();
//
//        } else {
//            ((Button) v).setText("系统起终点图标");
//            Toast.makeText(this,
//                    "将使用自定义起终点图标",
//                    Toast.LENGTH_SHORT).show();
//
//        }
//        useDefaultIcon = !useDefaultIcon;
//        routeOverlay.removeFromMap();
//        routeOverlay.addToMap();
//    }
//
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//    }
//
//    @Override
//    public void onGetWalkingRouteResult(final WalkingRouteResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//            AlertDialog.Builder builder = new AlertDialog.Builder(RoutePlanActivity.this);
//            builder.setTitle("提示");
//            builder.setMessage("检索地址有歧义，请重新设置。\n可通过getSuggestAddressInfo()接口获得建议查询信息");
//            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            builder.create().show();
//            return;
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
//            nodeIndex = -1;
//            mBtnPre.setVisibility(View.VISIBLE);
//            mBtnNext.setVisibility(View.VISIBLE);
//
//            if (result.getRouteLines().size() > 1) {
//                nowResultWalk = result;
//                if (!hasShownDialogue) {
//                    MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanActivity.this,
//                            result.getRouteLines(),
//                            RouteLineAdapter.Type.WALKING_ROUTE);
//                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            hasShownDialogue = false;
//                        }
//                    });
//                    myTransitDlg.setOnItemInDlgClickListener(new OnItemInDlgClickListener() {
//                        public void onItemClick(int position) {
//                            route = nowResultWalk.getRouteLines().get(position);
//                            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiDuMap);
//                            mBaiDuMap.setOnMarkerClickListener(overlay);
//                            routeOverlay = overlay;
//                            overlay.setData(nowResultWalk.getRouteLines().get(position));
//                            overlay.addToMap();
//                            overlay.zoomToSpan();
//                        }
//
//                    });
//                    myTransitDlg.show();
//                    hasShownDialogue = true;
//                }
//            } else if (result.getRouteLines().size() == 1) {
//                // 直接显示
//                route = result.getRouteLines().get(0);
//                WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiDuMap);
//                mBaiDuMap.setOnMarkerClickListener(overlay);
//                routeOverlay = overlay;
//                overlay.setData(result.getRouteLines().get(0));
//                overlay.addToMap();
//                overlay.zoomToSpan();
//
//            } else {
//                Log.d("route result", "结果数<0");
//            }
//
//        }
//
//    }
//
//    @Override
//    public void onGetTransitRouteResult(TransitRouteResult result) {
//
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//            result.getSuggestAddrInfo();
//            return;
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
//            nodeIndex = -1;
//            mBtnPre.setVisibility(View.VISIBLE);
//            mBtnNext.setVisibility(View.VISIBLE);
//
//
//            if (result.getRouteLines().size() > 1) {
//                nowResultTransit = result;
//                if (!hasShownDialogue) {
//                    MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanActivity.this,
//                            result.getRouteLines(),
//                            RouteLineAdapter.Type.TRANSIT_ROUTE);
//                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            hasShownDialogue = false;
//                        }
//                    });
//                    myTransitDlg.setOnItemInDlgClickListener(new OnItemInDlgClickListener() {
//                        public void onItemClick(int position) {
//
//                            route = nowResultTransit.getRouteLines().get(position);
//                            TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiDuMap);
//                            mBaiDuMap.setOnMarkerClickListener(overlay);
//                            routeOverlay = overlay;
//                            overlay.setData(nowResultTransit.getRouteLines().get(position));
//                            overlay.addToMap();
//                            overlay.zoomToSpan();
//                        }
//
//                    });
//                    myTransitDlg.show();
//                    hasShownDialogue = true;
//                }
//            } else if (result.getRouteLines().size() == 1) {
//                // 直接显示
//                route = result.getRouteLines().get(0);
//                TransitRouteOverlay overlay = new MyTransitRouteOverlay(mBaiDuMap);
//                mBaiDuMap.setOnMarkerClickListener(overlay);
//                routeOverlay = overlay;
//                overlay.setData(result.getRouteLines().get(0));
//                overlay.addToMap();
//                overlay.zoomToSpan();
//
//            } else {
//                Log.d("route result", "结果数<0");
//            }
//
//
//        }
//    }
//
//    @Override
//    public void onGetMassTransitRouteResult(MassTransitRouteResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//            // 起终点模糊，获取建议列表
//            result.getSuggestAddrInfo();
//            return;
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
//            nowResultMass = result;
//
//            nodeIndex = -1;
//            mBtnPre.setVisibility(View.VISIBLE);
//            mBtnNext.setVisibility(View.VISIBLE);
//
//            if (!hasShownDialogue) {
//                // 列表选择
//                MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanActivity.this,
//                        result.getRouteLines(),
//                        RouteLineAdapter.Type.MASS_TRANSIT_ROUTE);
//                nowResultMass = result;
//                myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        hasShownDialogue = false;
//                    }
//                });
//                myTransitDlg.setOnItemInDlgClickListener(new OnItemInDlgClickListener() {
//                    public void onItemClick(int position) {
//
//                        MyMassTransitRouteOverlay overlay = new MyMassTransitRouteOverlay(mBaiDuMap);
//                        mBaiDuMap.setOnMarkerClickListener(overlay);
//                        routeOverlay = overlay;
//                        massRoute = nowResultMass.getRouteLines().get(position);
//                        overlay.setData(nowResultMass.getRouteLines().get(position));
//
//                        MassTransitRouteLine line = nowResultMass.getRouteLines().get(position);
//                        overlay.setData(line);
//                        if (nowResultMass.getOrigin().getCityId() == nowResultMass.getDestination().getCityId()) {
//                            // 同城
//                            overlay.setSameCity(true);
//                        } else {
//                            // 跨城
//                            overlay.setSameCity(false);
//
//                        }
//                        mBaiDuMap.clear();
//                        overlay.addToMap();
//                        overlay.zoomToSpan();
//                    }
//
//                });
//                myTransitDlg.show();
//                hasShownDialogue = true;
//            }
//        }
//    }
//
//
//    @Override
//    public void onGetDrivingRouteResult(DrivingRouteResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//            result.getSuggestAddrInfo();
//            return;
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
//            nodeIndex = -1;
//
//
//            if (result.getRouteLines().size() > 1) {
//                nowResultDrive = result;
//                if (!hasShownDialogue) {
//                    MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanActivity.this,
//                            result.getRouteLines(),
//                            RouteLineAdapter.Type.DRIVING_ROUTE);
//                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            hasShownDialogue = false;
//                        }
//                    });
//                    myTransitDlg.setOnItemInDlgClickListener(new OnItemInDlgClickListener() {
//                        public void onItemClick(int position) {
//                            route = nowResultDrive.getRouteLines().get(position);
//                            DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiDuMap);
//                            mBaiDuMap.setOnMarkerClickListener(overlay);
//                            routeOverlay = overlay;
//                            overlay.setData(nowResultDrive.getRouteLines().get(position));
//                            overlay.addToMap();
//                            overlay.zoomToSpan();
//                        }
//
//                    });
//                    myTransitDlg.show();
//                    hasShownDialogue = true;
//                }
//            } else if (result.getRouteLines().size() == 1) {
//                route = result.getRouteLines().get(0);
//                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiDuMap);
//                routeOverlay = overlay;
//                mBaiDuMap.setOnMarkerClickListener(overlay);
//                overlay.setData(result.getRouteLines().get(0));
//                overlay.addToMap();
//                overlay.zoomToSpan();
//                mBtnPre.setVisibility(View.VISIBLE);
//                mBtnNext.setVisibility(View.VISIBLE);
//            } else {
//                Log.d("route result", "结果数<0");
//            }
//
//        }
//    }
//
//    @Override
//    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
//
//    }
//
//    @Override
//    public void onGetBikingRouteResult(BikingRouteResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
//            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
//            result.getSuggestAddrInfo();
//            AlertDialog.Builder builder = new AlertDialog.Builder(RoutePlanActivity.this);
//            builder.setTitle("提示");
//            builder.setMessage("检索地址有歧义，请重新设置。\n可通过getSuggestAddressInfo()接口获得建议查询信息");
//            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            builder.create().show();
//            return;
//        }
//        if (result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
//            nodeIndex = -1;
//            mBtnPre.setVisibility(View.VISIBLE);
//            mBtnNext.setVisibility(View.VISIBLE);
//
//            if (result.getRouteLines().size() > 1) {
//                nowResultBike = result;
//                if (!hasShownDialogue) {
//                    MyTransitDlg myTransitDlg = new MyTransitDlg(RoutePlanActivity.this,
//                            result.getRouteLines(),
//                            RouteLineAdapter.Type.DRIVING_ROUTE);
//                    myTransitDlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            hasShownDialogue = false;
//                        }
//                    });
//                    myTransitDlg.setOnItemInDlgClickListener(new OnItemInDlgClickListener() {
//                        public void onItemClick(int position) {
//                            route = nowResultBike.getRouteLines().get(position);
//                            BikingRouteOverlay overlay = new MyBikingRouteOverlay(mBaiDuMap);
//                            mBaiDuMap.setOnMarkerClickListener(overlay);
//                            routeOverlay = overlay;
//                            overlay.setData(nowResultBike.getRouteLines().get(position));
//                            overlay.addToMap();
//                            overlay.zoomToSpan();
//                        }
//
//                    });
//                    myTransitDlg.show();
//                    hasShownDialogue = true;
//                }
//            } else if (result.getRouteLines().size() == 1) {
//                route = result.getRouteLines().get(0);
//                BikingRouteOverlay overlay = new MyBikingRouteOverlay(mBaiDuMap);
//                routeOverlay = overlay;
//                mBaiDuMap.setOnMarkerClickListener(overlay);
//                overlay.setData(result.getRouteLines().get(0));
//                overlay.addToMap();
//                overlay.zoomToSpan();
//                mBtnPre.setVisibility(View.VISIBLE);
//                mBtnNext.setVisibility(View.VISIBLE);
//            } else {
//                Log.d("route result", "结果数<0");
//            }
//
//        }
//    }
//
//    // 定制RouteOverly
//    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {
//
//        private MyDrivingRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//            }
//            return null;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//            }
//            return null;
//        }
//    }
//
//    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {
//
//        private MyWalkingRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//            }
//            return null;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//            }
//            return null;
//        }
//    }
//
//    private class MyTransitRouteOverlay extends TransitRouteOverlay {
//
//        private MyTransitRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//            }
//            return null;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//            }
//            return null;
//        }
//    }
//
//    private class MyBikingRouteOverlay extends BikingRouteOverlay {
//        private MyBikingRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//            }
//            return null;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//            }
//            return null;
//        }
//
//
//    }
//
//    private class MyMassTransitRouteOverlay extends MassTransitRouteOverlay {
//        private MyMassTransitRouteOverlay(BaiduMap baiduMap) {
//            super(baiduMap);
//        }
//
//        @Override
//        public BitmapDescriptor getStartMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_st);
//            }
//            return null;
//        }
//
//        @Override
//        public BitmapDescriptor getTerminalMarker() {
//            if (useDefaultIcon) {
//                return BitmapDescriptorFactory.fromResource(R.drawable.icon_en);
//            }
//            return null;
//        }
//
//
//    }
//
//    @Override
//    public void onMapClick(LatLng point) {
//        mBaiDuMap.hideInfoWindow();
//    }
//
//    @Override
//    public boolean onMapPoiClick(MapPoi poi) {
//        return false;
//    }
//
//    @Override
//    protected void onPause() {
//        mMapView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        mMapView.onResume();
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (mSearch != null) {
//            mSearch.destroy();
//        }
//        mMapView.onDestroy();
//        super.onDestroy();
//    }
//
//    // 响应DLg中的List item 点击
//    interface OnItemInDlgClickListener {
//        void onItemClick(int position);
//    }
//
//    // 供路线选择的Dialog
//    private class MyTransitDlg extends Dialog {
//
//        private List<? extends RouteLine> mtransitRouteLines;
//        private ListView transitRouteList;
//        private RouteLineAdapter mTransitAdapter;
//
//        OnItemInDlgClickListener onItemInDlgClickListener;
//
//        private MyTransitDlg(Context context, int theme) {
//            super(context, theme);
//        }
//
//        private MyTransitDlg(Context context, List<? extends RouteLine> transitRouteLines, RouteLineAdapter.Type
//                type) {
//            this(context, 0);
//            mtransitRouteLines = transitRouteLines;
//            mTransitAdapter = new RouteLineAdapter(context, mtransitRouteLines, type);
//            requestWindowFeature(Window.FEATURE_NO_TITLE);
//        }
//
//        @Override
//        public void setOnDismissListener(OnDismissListener listener) {
//            super.setOnDismissListener(listener);
//        }
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_transit_dialog);
//
//            transitRouteList = (ListView) findViewById(R.id.transitList);
//            transitRouteList.setAdapter(mTransitAdapter);
//
//            transitRouteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    onItemInDlgClickListener.onItemClick(position);
//                    mBtnPre.setVisibility(View.VISIBLE);
//                    mBtnNext.setVisibility(View.VISIBLE);
//                    dismiss();
//                    hasShownDialogue = false;
//                }
//            });
//        }
//
//        private void setOnItemInDlgClickListener(OnItemInDlgClickListener itemListener) {
//            onItemInDlgClickListener = itemListener;
//        }
//
//    }
//
//    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
//        public void onGetGeoCodeResult(GeoCodeResult result) {
//            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//                //没有检索到结果
//                ToastUtils.showCenter("没有检索到结果");
//            }
//            //获取地理编码结果
//            mBaiDuMap.clear();
//            if (result != null) {
//                mBaiDuMap.addOverlay(new MarkerOptions().position(result.getLocation())
//                        .icon(BitmapDescriptorFactory
//                                .fromResource(R.drawable.icon_en)));
//            }
//            if (result != null) {
//                mBaiDuMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
//                        .getLocation()));
//            }
//            if (result != null) {
//                endLatLng = result.getLocation();
//            }
//
//        }
//
//        @Override
//        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//
//        }
//    };
//}
