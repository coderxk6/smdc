// pages/address/list/list.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addressList: [], // 地址列表数据
    selectedAddressId: null, // 从其他页面选择地址时传入的已选地址ID
    isSelecting: false, // 是否处于选择模式（例如从订单确认页跳转过来选择地址）
  },

  /**
   * 生命周期函数--监听页面加载
   * @param {Object} options - 页面启动参数
   */
  onLoad: function (options) {
    if (options.selectedId) {
      // 如果是从选择地址模式进入，记录已选地址ID
      this.setData({
        selectedAddressId: options.selectedId,
        isSelecting: true
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
  onShow: function () {
    this.loadAddressList(); // 页面显示时加载地址列表，确保数据最新
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

  },

  /**
   * 加载地址列表数据
   */
  loadAddressList: async function () {
    try {
      const baseUrl = 'http://localhost:8080'; // 替换为你的后端服务地址
      const token = wx.getStorageSync('token') || '';

      const res = await wx.request({
        url: `${baseUrl}/mini/user/address/list`,
        method: 'GET',
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
      });

      if (res && res.data && res.data.code === 1) {
        this.setData({
          addressList: res.data.data
        });
      } else {
        wx.showToast({
          title: (res && res.data && res.data.msg) || '获取地址失败',
          icon: 'none'
        });
      }
    } catch (error) {
      console.error('加载地址列表失败:', error);
      wx.showToast({
        title: '网络请求失败',
        icon: 'none'
      });
    }
  },

  /**
   * 跳转到新增地址页面
   */
  addAddress: function () {
    wx.navigateTo({
      url: '/pages/address/edit/edit'
    });
  },

  /**
   * 跳转到编辑地址页面
   * @param {Object} e - 事件对象，包含地址ID
   */
  editAddress: function (e) {
    const addressId = e.currentTarget.dataset.id;
    wx.navigateTo({
      url: `/pages/address/edit/edit?id=${addressId}`
    });
  },

  /**
   * 删除收货地址
   * @param {Object} e - 事件对象，包含地址ID
   */
  deleteAddress: function (e) {
    const addressId = e.currentTarget.dataset.id;
    wx.showModal({
      title: '提示',
      content: '确定要删除该地址吗？',
      success: async (res) => {
        if (res.confirm) {
          try {
            const baseUrl = 'http://localhost:8080';
            const token = wx.getStorageSync('token') || '';
            const result = await wx.request({
              url: `${baseUrl}/mini/user/address/delete/${addressId}`,
              method: 'POST',
              header: {
                'Content-Type': 'application/json',
                'token': token
              },
            });

            if (result && result.data && result.data.code === 1) {
              wx.showToast({
                title: '删除成功',
                icon: 'success'
              });
              this.loadAddressList(); // 刷新地址列表
            } else {
              wx.showToast({
                title: (result && result.data && result.data.msg) || '删除失败',
                icon: 'none'
              });
            }
          } catch (error) {
            console.error('删除地址失败:', error);
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
   * 设置默认地址
   * @param {Object} e - 事件对象，包含地址ID
   */
  setDefaultAddress: async function (e) {
    const addressId = e.currentTarget.dataset.id;
    try {
      const baseUrl = 'http://localhost:8080';
      const token = wx.getStorageSync('token') || '';
      const result = await wx.request({
        url: `${baseUrl}/mini/user/address/default/${addressId}`,
        method: 'POST',
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
      });

      if (result && result.data && result.data.code === 1) {
        wx.showToast({
          title: '设置成功',
          icon: 'success'
        });
        this.loadAddressList(); // 刷新地址列表
      } else {
        wx.showToast({
          title: (result && result.data && result.data.msg) || '设置失败',
          icon: 'none'
        });
      }
    } catch (error) {
      console.error('设置默认地址失败:', error);
      wx.showToast({
        title: '网络错误',
        icon: 'none'
      });
    }
  },

  /**
   * 选择地址（用于从订单确认页等选择地址的场景）
   * @param {Object} e - 事件对象，包含地址数据
   */
  selectAddress: function (e) {
    if (!this.data.isSelecting) {
      // 如果不是选择模式，点击不触发选择行为
      return;
    }
    const selectedAddress = e.currentTarget.dataset.address;
    // 返回上一个页面并传递选中的地址信息
    const eventChannel = this.getOpenerEventChannel();
    eventChannel.emit('acceptAddress', {
      address: selectedAddress
    });
    wx.navigateBack();
  }
})