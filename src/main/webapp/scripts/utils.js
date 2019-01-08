/*
 * Copyright 2012-2019 Andrey Grigorov, Anton Grigorov
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
Utils = {
    htmlEncode: function (value) {
        return $('<div/>').text(value).html();
    }
};

(function ($) {
    $.ExtDialog = function (params) {
        if (!$.DialogOpened) {
            $.DialogOpened = true;
        } else {
            return;
        }

        params = $.extend({'position': {'zone': 'center'}, 'overlay': true}, params);

        var buttonsHTML = '<div';

        // Buttons position
        if (params.buttonsAlign) {
            buttonsHTML += ' style=" float: ' + params.buttonsAlign + ';">';
        } else {
            buttonsHTML += '>';
        }

        $.each(params.buttons, function (name, obj) {
            // Generating the markup for the buttons

            buttonsHTML += '<button>' + name + '</button>';

            if (!obj.action) {
                obj.action = function () {
                };
            }
        });

        buttonsHTML += '</div>';

        var markup = [
            // If overlay is true, set it

            '<div id="dialogOverlay">',
            '<div id="dialogBox" class="dialog">',
            '<div class="header">',
            params.title,
            (params.closeButton) ? ('<div><button><i class="icon-cancel-2"></i></button></div>') : (''),
            '</div>',
            '<div class="error"></div>',
            '<div class="content">', params.content, '</div>',
            '<div class="action" id="dialogButtons">',
            buttonsHTML,
            '</div></div></div>'
        ].join('');

        $(markup).hide().appendTo('body').fadeIn();

        if (!params.overlay) {
            $('#dialogOverlay').css('background-color', 'rgba(255, 255, 255, 0)');
        }

        var dialogBox = $('#dialogBox');
        // Setting initial position
        if (params.position.zone == "left") {
            dialogBox.css("top", Math.max(0, (($(window).height() - dialogBox.outerHeight()) / 3) +
            $(window).scrollTop()) + "px");
            dialogBox.css("left", 0);
        }
        else if (params.position.zone == "right") {
            dialogBox.css("top", Math.max(0, (($(window).height() - dialogBox.outerHeight()) / 3) +
            $(window).scrollTop()) + "px");
            dialogBox.css("left", Math.max(0, (($(window).width() - dialogBox.outerWidth())) +
            $(window).scrollLeft()) + "px");
        }
        else {
            dialogBox.css("top", (params.position.offsetY) ? (params.position.offsetY) : (Math.max(0, (($(window).height() - dialogBox.outerHeight()) / 3) +
            $(window).scrollTop()) + "px"));
            dialogBox.css("left", (params.position.offsetX) ? (params.position.offsetX) : (Math.max(0, (($(window).width() - dialogBox.outerWidth()) / 2) +
            $(window).scrollLeft()) + "px"));
        }

        if (params.draggable) {
            // Make draggable the window

            dialogBox.find('div.header').css('cursor', 'move').on("mousedown", function (e) {
                var $drag = $(this).addClass('active-handle').parent().addClass('draggable');

                var z_idx = $drag.css('z-index'),
                    drg_h = $drag.outerHeight(),
                    drg_w = $drag.outerWidth(),
                    pos_y = $drag.offset().top + drg_h - e.pageY,
                    pos_x = $drag.offset().left + drg_w - e.pageX;
                $drag.css('z-index', 99999).parents().on("mousemove", function (e) {
                    $('.draggable').offset({
                        top: e.pageY + pos_y - drg_h,
                        left: e.pageX + pos_x - drg_w
                    }).on("mouseup", function () {
                        $(this).removeClass('draggable').css('z-index', z_idx);
                    });
                });
                e.preventDefault(); // disable selection
            }).on("mouseup", function () {
                $(this).removeClass('active-handle').parent().removeClass('draggable');
            });
        }

        dialogBox.find('.header button').click(function () {
            // Bind close button to hide dialog

            $.ExtDialog.hide();
            return false;
        });

        var buttons = dialogBox.find('.action button'),
            i = 0;

        $.each(params.buttons, function (name, obj) {
            buttons.eq(i++).click(function () {
                // Calling function and hide the dialog   

                obj.action();
                if (obj.closeAfterAction) {
                    $.ExtDialog.hideWithFadeOut();
                }
                return false;
            });
        });
    };

    $.ExtDialog.hide = function () {
        $.DialogOpened = false;
        $('#dialogOverlay').remove();
    };

    $.ExtDialog.hideWithFadeOut = function () {
        $('#dialogOverlay').fadeOut(function(){
            $.DialogOpened = false;
            $(this).remove();
        });
    };
})(jQuery);