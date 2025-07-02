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
    currentCategoryId: null, // 当前选中的分类ID
    isLoading: true, // 添加加载状态
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
    this.setData({
      isLoading: true
    });

    wx.request({
      url: app.globalData.baseUrl + '/mini/home/data',
      method: 'GET',
      success: (res) => {
        if (res.data.code === 1) {
          const homeData = res.data.data;
          this.setData({
            homeData: homeData,
            // 默认选中第一个分类
            currentCategoryId: homeData.categories && homeData.categories.length > 0 ? homeData.categories[0].id : null,
            isLoading: false
          });
        } else {
          this.setData({
            isLoading: false
          });
          wx.showToast({
            title: res.data.msg || '获取首页数据失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('获取首页数据请求失败', err);
        this.setData({
          isLoading: false
        });
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 下拉刷新
   */
  onPullDownRefresh () {
    this.getHomeData();
    wx.stopPullDownRefresh();
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

    // 更新当前选中的分类
    this.setData({
      currentCategoryId: categoryId
    });

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
    return {
      title: this.data.homeData.shopInfo ? this.data.homeData.shopInfo.name : '扫码点餐',
      path: '/pages/index/index'
    };
  },

  addToCart (e) {
    wx.showToast({
      title: '已加入购物车',
      icon: 'success'
    });
  },
});