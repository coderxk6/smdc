// pages/user/about/about.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    shopInfo: {
      name: 'Loading...',
      slogan: '...',
      logo: '/images/default-logo.png', // 默认logo
      address: '加载中...',
      phone: '暂无',
      businessHours: '暂无',
      longitude: '',
      latitude: '',
    } // 店铺信息
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.loadShopInfo();
  },

  /**
   * 加载店铺信息
   */
  loadShopInfo: async function () {
    try {
      const baseUrl = 'http://localhost:8080'; // 替换为你的后端服务地址
      const token = wx.getStorageSync('token') || '';

      const res = await wx.request({
        url: `${baseUrl}/mini/shop/info`,
        method: 'GET', // 修改为GET方法
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
      });

      if (res && res.data && res.data.code === 1) {
        this.setData({
          shopInfo: res.data.data
        });
      } else {
        wx.showToast({
          title: (res && res.data && res.data.msg) || '获取店铺信息失败',
          icon: 'none'
        });
      }
    } catch (error) {
      console.error('加载店铺信息失败:', error);
      wx.showToast({
        title: '网络请求失败',
        icon: 'none'
      });
    }
  },

  /**
   * 拨打电话
   */
  callPhone: function () {
    const phone = this.data.shopInfo.phone;
    if (phone && phone !== '暂无') {
      wx.makePhoneCall({
        phoneNumber: phone
      });
    } else {
      wx.showToast({
        title: '暂无联系电话',
        icon: 'none'
      });
    }
  },

  /**
   * 打开地图导航
   */
  openLocation: function () {
    const { name, address, latitude, longitude } = this.data.shopInfo;
    if (latitude && longitude) {
      wx.openLocation({
        latitude: parseFloat(latitude),
        longitude: parseFloat(longitude),
        name: name || '店铺位置',
        address: address || '店铺地址',
        scale: 18
      });
    } else {
      wx.showToast({
        title: '暂无店铺位置信息',
        icon: 'none'
      });
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage () {

  }
})