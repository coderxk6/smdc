const images = require('../static/images.json');

const ImageUtil = {
  // 获取图片路径
  getImagePath (key) {
    const keys = key.split('.');
    let value = images;
    for (const k of keys) {
      if (value[k] === undefined) {
        console.warn(`Image key not found: ${key}`);
        return images.common.error;
      }
      value = value[k];
    }
    return value;
  },

  // 获取默认图片
  getDefaultImage (type) {
    const defaults = {
      dish: images.dish.default,
      avatar: images.user.default_avatar,
      shop: images.shop.logo,
      empty: images.common.empty,
      error: images.common.error,
      loading: images.common.loading
    };
    return defaults[type] || images.common.error;
  },

  // 获取订单状态图标
  getOrderStatusIcon (status) {
    const statusMap = {
      1: 'pending',   // 待付款
      2: 'paid',      // 待接单
      3: 'processing', // 待上菜
      4: 'completed',  // 已完成
      5: 'cancelled'   // 已取消
    };
    return this.getImagePath(`order.status.${statusMap[status] || 'pending'}`);
  },

  // 获取支付方式图标
  getPaymentIcon (method) {
    const methodMap = {
      1: 'wechat',    // 微信支付
      2: 'alipay'     // 支付宝支付
    };
    return this.getImagePath(`order.payment.${methodMap[method] || 'wechat'}`);
  },

  // 获取用户中心图标
  getUserCenterIcon (type) {
    return this.getImagePath(`user.center.${type}`);
  },

  // 获取分类图标
  getCategoryIcon (type) {
    return this.getImagePath(`dish.category.${type}`);
  }
};

module.exports = ImageUtil; 