const app = getApp();

Page({
  /**
   * 页面的初始数据
   */
  data: {
    searchText: '',
    searchResults: [],
    searchHistory: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    this.loadSearchHistory();
  },

  /**
   * 处理搜索输入
   */
  onSearchInput (e) {
    this.setData({
      searchText: e.detail.value
    });
  },

  /**
   * 确认搜索
   */
  onSearchConfirm () {
    const keyword = this.data.searchText.trim();
    if (keyword) {
      this.performSearch(keyword);
      this.saveSearchHistory(keyword);
    } else {
      wx.showToast({
        title: '请输入搜索关键词',
        icon: 'none'
      });
    }
  },

  /**
   * 执行搜索请求
   */
  performSearch (keyword) {
    wx.showLoading({
      title: '搜索中',
    });
    wx.request({
      url: app.globalData.baseUrl + '/mini/dish/search?keyword=' + keyword,
      method: 'GET',
      success: (res) => {
        wx.hideLoading();
        if (res.data.code === 1) {
          this.setData({
            searchResults: res.data.data
          });
        } else {
          wx.showToast({
            title: res.data.msg || '搜索失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        wx.hideLoading();
        console.error('搜索请求失败', err);
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 取消搜索，返回上一页
   */
  onCancelSearch () {
    wx.navigateBack({
      delta: 1
    });
  },

  /**
   * 加载搜索历史
   */
  loadSearchHistory () {
    const history = wx.getStorageSync('searchHistory') || [];
    this.setData({
      searchHistory: history
    });
  },

  /**
   * 保存搜索历史
   */
  saveSearchHistory (keyword) {
    let history = this.data.searchHistory;
    // 移除重复项
    history = history.filter(item => item !== keyword);
    // 添加到最前面
    history.unshift(keyword);
    // 限制历史记录数量
    if (history.length > 10) {
      history = history.slice(0, 10);
    }
    this.setData({
      searchHistory: history
    });
    wx.setStorageSync('searchHistory', history);
  },

  /**
   * 清空搜索历史
   */
  clearSearchHistory () {
    wx.showModal({
      title: '提示',
      content: '确定清空搜索历史吗？',
      success: (res) => {
        if (res.confirm) {
          this.setData({
            searchHistory: []
          });
          wx.removeStorageSync('searchHistory');
        }
      }
    });
  },

  /**
   * 点击历史记录或热门搜索进行快速搜索
   */
  quickSearch (e) {
    const keyword = e.currentTarget.dataset.keyword;
    this.setData({
      searchText: keyword
    });
    this.performSearch(keyword);
    this.saveSearchHistory(keyword);
  },

  /**
   * 跳转到菜品详情页面
   */
  goToDishDetail (e) {
    const dishId = e.currentTarget.dataset.dishId;
    wx.navigateTo({
      url: `/pages/dish/dish-detail?id=${dishId}`
    });
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage () {

  }
}) 