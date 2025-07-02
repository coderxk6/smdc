const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    shopInfo: null,
    markers: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    this.getShopInfo();
  },

  /**
   * 获取店铺信息
   */
  getShopInfo () {
    wx.request({
      url: app.globalData.baseUrl + '/mini/shop/info',
      method: 'GET',
      success: (res) => {
        if (res.data.code === 1) {
          const shopInfo = res.data.data;
          this.setData({
            shopInfo: shopInfo
          });
          // 如果有经纬度，设置地图标记
          if (shopInfo.latitude && shopInfo.longitude) {
            this.setData({
              markers: [{
                id: 1,
                latitude: shopInfo.latitude,
                longitude: shopInfo.longitude,
                width: 30,
                height: 30
              }]
            });
          }
        } else {
          wx.showToast({
            title: res.data.msg || '获取店铺信息失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('获取店铺信息请求失败', err);
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage () {

  }
}) 