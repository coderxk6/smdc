const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    homeData: {
      shopInfo: null,
      categories: [],
      recommendDishes: [],
      hotDishes: [],
      newDishes: [],
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    this.getHomeData();
  },

  /**
   * 获取首页数据
   */
  getHomeData () {
    wx.showLoading({
      title: '加载中',
    });
    wx.request({
      url: app.globalData.baseUrl + '/mini/home/data',
      method: 'GET',
      success: (res) => {
        wx.hideLoading();
        if (res.data.code === 1) {
          this.setData({
            homeData: res.data.data
          });
        } else {
          wx.showToast({
            title: res.data.msg || '获取首页数据失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        wx.hideLoading();
        console.error('获取首页数据请求失败', err);
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 跳转到店铺信息页面
   */
  goToShopInfo () {
    wx.navigateTo({
      url: '/pages/shopinfo/shopinfo'
    });
  },

  /**
   * 跳转到搜索页面
   */
  goToSearch () {
    wx.navigateTo({
      url: '/pages/search/search'
    });
  },

  /**
   * 跳转到分类页面
   */
  goToCategory (e) {
    const categoryId = e.currentTarget.dataset.categoryId;
    const categoryName = e.currentTarget.dataset.categoryName;
    wx.navigateTo({
      url: `/pages/dish/dish?categoryId=${categoryId}&categoryName=${categoryName}`
    });
  },

  /**
   * 跳转到菜品详情页面
   */
  goToDishDetail (e) {
    const dishId = e.currentTarget.dataset.dishId;
    wx.navigateTo({
      url: `/pages/dish/dish-detail?id=${dishId}` // 假设菜品详情页为 dish-detail
    });
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage () {

  }
}); 