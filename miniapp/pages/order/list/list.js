// pages/order/list/list.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 订单状态筛选标签
    tabs: [{
      id: 0,
      name: '全部',
      status: null // null 表示全部状态
    }, {
      id: 1,
      name: '待付款',
      status: 1
    }, {
      id: 2,
      name: '待接单',
      status: 2
    }, {
      id: 3,
      name: '待上菜',
      status: 3
    }, {
      id: 4,
      name: '已完成',
      status: 4
    }, {
      id: 5,
      name: '已取消',
      status: 5
    },],
    activeTab: 0, // 当前选中的tab索引
    // 订单状态映射（用于显示）
    orderStatusMap: {
      1: '待付款',
      2: '待接单',
      3: '待上菜',
      4: '已完成',
      5: '已取消',
    },
    orderList: [], // 订单数据列表
    page: 1, // 当前页码
    pageSize: 10, // 每页加载数量
    hasMore: true, // 是否还有更多数据
    loading: false, // 是否正在加载中
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad (options) {
    // 可以在这里根据options.status参数，设置默认选中的tab
    const initialStatus = options.status ? parseInt(options.status) : null;
    if (initialStatus !== null) {
      const index = this.data.tabs.findIndex(tab => tab.status === initialStatus);
      if (index !== -1) {
        this.setData({
          activeTab: index
        });
      }
    }
    this.loadOrderList();
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
    // 从其他页面返回时，刷新订单列表，确保数据最新
    if (typeof this.getTabBar === 'function' && this.getTabBar()) {
      this.getTabBar().setData({
        selected: 1 // 选中底部tabbar的"我的" (假设订单列表页不是tabbar页面，这里为了演示，如果它是tabbar页面，需要调整app.json)
      })
    }
    this.loadOrderList(); // 重新加载数据
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
      orderList: [],
      hasMore: true,
      loading: false,
    });
    this.loadOrderList().then(() => {
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
      this.loadOrderList(true); // 加载更多
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage () {

  },

  /**
   * 切换订单状态Tab
   * @param {Object} e - 事件对象
   */
  onTabChange: function (e) {
    const index = e.detail.index;
    this.setData({
      activeTab: index,
      page: 1, // 切换tab时重置页码
      orderList: [], // 清空旧数据
      hasMore: true,
      loading: false,
    });
    this.loadOrderList(); // 加载新数据
  },

  /**
   * 加载订单列表数据
   * @param {boolean} isLoadMore - 是否是加载更多模式
   */
  loadOrderList: async function (isLoadMore = false) {
    if (this.data.loading) return; // 避免重复加载
    this.setData({
      loading: true
    });

    try {
      const currentStatus = this.data.tabs[this.data.activeTab].status;
      const baseUrl = 'http://localhost:8080'; // 替换为你的后端服务地址
      const token = wx.getStorageSync('token') || '';

      const res = await wx.request({
        url: `${baseUrl}/mini/order/list`,
        method: 'GET',
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
        data: {
          page: this.data.page,
          pageSize: this.data.pageSize,
          status: currentStatus // 根据当前tab筛选状态
        }
      });

      if (res && res.data && res.data.code === 1) {
        const { records, total } = res.data.data;
        const newOrderList = [...this.data.orderList, ...records];
        this.setData({
          orderList: newOrderList,
          hasMore: newOrderList.length < total,
        });
      } else {
        wx.showToast({
          title: (res && res.data && res.data.msg) || '获取订单失败',
          icon: 'none'
        });
        this.setData({
          hasMore: false
        });
      }
    } catch (error) {
      console.error('加载订单列表失败:', error);
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
   * 跳转到订单详情页
   * @param {Object} e - 事件对象，包含订单ID
   */
  navigateToDetail: function (e) {
    const orderId = e.currentTarget.dataset.orderid;
    wx.navigateTo({
      url: `/pages/order/detail/detail?id=${orderId}`
    });
  },

  /**
   * 取消订单
   * @param {Object} e - 事件对象，包含订单ID
   */
  cancelOrder: async function (e) {
    const orderId = e.currentTarget.dataset.orderid;
    wx.showModal({
      title: '提示',
      content: '确定要取消该订单吗？',
      success: async (res) => {
        if (res.confirm) {
          try {
            const baseUrl = 'http://localhost:8080'; // 替换为你的后端服务地址
            const token = wx.getStorageSync('token') || '';
            const result = await wx.request({
              url: `${baseUrl}/mini/order/cancel`,
              method: 'POST',
              header: {
                'Content-Type': 'application/json',
                'token': token
              },
              data: {
                id: orderId // 订单ID
              }
            });

            if (result && result.data && result.data.code === 1) {
              wx.showToast({
                title: '订单已取消',
                icon: 'success'
              });
              this.loadOrderList(); // 刷新列表
            } else {
              wx.showToast({
                title: (result && result.data && result.data.msg) || '取消失败',
                icon: 'none'
              });
            }
          } catch (error) {
            console.error('取消订单失败:', error);
            wx.showToast({
              title: '网络错误',
              icon: 'none'
            });
          }
        }
      }
    });
  },

  /**
   * 支付订单
   * @param {Object} e - 事件对象，包含订单ID
   */
  payOrder: function (e) {
    const orderId = e.currentTarget.dataset.orderid;
    wx.navigateTo({
      url: `/pages/order/confirm/confirm?id=${orderId}&action=pay` // 跳转到确认页并带上支付标记
    });
  },

  /**
   * 评价订单
   * @param {Object} e - 事件对象，包含订单ID
   */
  commentOrder: function (e) {
    const orderId = e.currentTarget.dataset.orderid;
    wx.navigateTo({
      url: `/pages/order/comment?id=${orderId}` // 跳转到订单评价页
    });
  }
})