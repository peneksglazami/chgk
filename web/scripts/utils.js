Utils = {
    htmlEncode: function (value) {
        return $('<div/>').text(value).html();
    }
}