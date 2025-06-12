// pages/user/favorite/favorite.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    favoriteList: [], // 收藏的菜品数据列表
    page: 1, // 当前页码
    pageSize: 10, // 每页加载数量
    hasMore: true, // 是否还有更多数据
    loading: false, // 是否正在加载中
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    this.loadFavoriteList();
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
    this.loadFavoriteList(); // 页面显示时刷新列表，确保数据最新
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
    this.setData({
      page: 1,
      favoriteList: [],
      hasMore: true,
      loading: false,
    });
    this.loadFavoriteList().then(() => {
      wx.stopPullDownRefresh(); // 数据加载完成后停止下拉刷新
    });
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom () {
    if (this.data.hasMore && !this.data.loading) {
      this.setData({
        page: this.data.page + 1
      });
      this.loadFavoriteList(true); // 加载更多
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage () {

  },

  /**
   * 加载收藏列表数据
   * @param {boolean} isLoadMore - 是否是加载更多模式
   */
  loadFavoriteList: async function (isLoadMore = false) {
    if (this.data.loading) return; // 避免重复加载
    this.setData({
      loading: true
    });

    try {
      const baseUrl = 'http://localhost:8080'; // 替换为你的后端服务地址
      const token = wx.getStorageSync('token') || '';

      const res = await wx.request({
        url: `${baseUrl}/mini/dish/favorite/list`,
        method: 'GET',
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
        data: {
          page: this.data.page,
          pageSize: this.data.pageSize,
        }
      });

      if (res && res.data && res.data.code === 1) {
        const { records, total } = res.data.data;
        const newFavoriteList = [...this.data.favoriteList, ...records];
        this.setData({
          favoriteList: newFavoriteList,
          hasMore: newFavoriteList.length < total,
        });
      } else {
        wx.showToast({
          title: (res && res.data && res.data.msg) || '获取收藏失败',
          icon: 'none'
        });
        this.setData({
          hasMore: false
        });
      }
    } catch (error) {
      console.error('加载收藏列表失败:', error);
      wx.showToast({
        title: '网络请求失败',
        icon: 'none'
      });
      this.setData({
        hasMore: false
      });
    } finally {
      this.setData({
        loading: false
      });
    }
  },

  /**
   * 跳转到菜品详情页
   * @param {Object} e - 事件对象，包含菜品ID
   */
  navigateToDishDetail: function (e) {
    const dishId = e.currentTarget.dataset.dishid;
    wx.navigateTo({
      url: `/pages/dish/detail?id=${dishId}`
    });
  },

  /**
   * 取消收藏
   * @param {Object} e - 事件对象，包含菜品ID
   */
  cancelFavorite: async function (e) {
    const dishId = e.currentTarget.dataset.dishid;
    wx.showModal({
      title: '提示',
      content: '确定要取消收藏吗？',
      success: async (res) => {
        if (res.confirm) {
          try {
            const baseUrl = 'http://localhost:8080';
            const token = wx.getStorageSync('token') || '';
            const result = await wx.request({
              url: `${baseUrl}/mini/dish/favorite/cancel/${dishId}`,
              method: 'POST',
              header: {
                'Content-Type': 'application/json',
                'token': token
              },
            });

            if (result && result.data && result.data.code === 1) {
              wx.showToast({
                title: '已取消收藏',
                icon: 'success'
              });
              this.loadFavoriteList(); // 刷新列表
            } else {
              wx.showToast({
                title: (result && result.data && result.data.msg) || '取消失败',
                icon: 'none'
              });
            }
          } catch (error) {
            console.error('取消收藏失败:', error);
            wx.showToast({
              title: '网络错误',
              icon: 'none'
            });
          }
        }
      }
    });
  }
})