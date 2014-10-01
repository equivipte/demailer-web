<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="widget-header widget-header-flat widget-header-small">
    <h5>
        <i class="icon-signal"></i>
        <spring:message code="label.campaign.activity"/>
    </h5>

</div>
<div class="widget-body">
    <div class="widget-main">
        <div class="infobox infobox-green ">
            <div class="infobox-icon">
                <i class="icon-eye-open"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">8/50</span>

                <div class="infobox-content"><spring:message code="label.campaign.opened"/></div>
            </div>
            <div class="badge badge-success">
                +32%
                <i class="icon-arrow-up"></i>
            </div>
        </div>

        <div class="infobox infobox-blue">
            <div class="infobox-icon">
                <i class="icon-inbox"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">50/50</span>

                <div class="infobox-content"><spring:message code="label.campaign.delivered"/></div>
            </div>

            <div class="badge badge-info">
                +32%
                <i class="icon-arrow-up"></i>
            </div>
        </div>

        <div class="infobox infobox-red">
            <div class="infobox-icon">
                <i class="icon-remove"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">7</span>

                <div class="infobox-content"><spring:message code="label.campaign.failed"/></div>
            </div>
        </div>
        <div class="infobox infobox-red">
            <div class="infobox-icon">
                <i class="icon-remove-sign"></i>
            </div>

            <div class="infobox-data">
                <span class="infobox-data-number">7</span>

                <div class="infobox-content"><spring:message code="label.campaign.unsubscribed"/></div>
            </div>
        </div>
    </div>
</div>
</br>
</br>
<div class="widget-header widget-header-flat widget-header-small">
    <h5>
        <i class="icon-eye-open"></i>
        <spring:message code="label.campaign.reading_environment"/>
    </h5>

</div>

<div class="widget-body">
    <div class="widget-main">
        <div id="piechart-placeholder"></div>

        <div class="hr hr8 hr-double"></div>

        <div class="clearfix">
            <div class="grid3">
                <span class="grey">
                    <i class="icon-mobile-phone icon-2x blue"></i>
                </span>
                <h4 class="bigger pull-right">1,255</h4>
            </div>

            <div class="grid3">
                <span class="grey">
                    <i class="icon-desktop icon-2x green"></i>
                </span>
                <h4 class="bigger pull-right">941</h4>
            </div>

            <div class="grid3">
                <span class="grey">
                    <i class="icon-question-sign icon-2x red"></i>
                </span>
                <h4 class="bigger pull-right">1,050</h4>
            </div>
        </div>
    </div>
    <!-- /widget-main -->
</div>
<div class="clearfix form-actions">
    <div class="col-md-offset-3 col-md-9">
        <button id="back_to_subscriber_list" class="btn" onclick="backToCampaignList()" type="reset">
            <i class="icon-undo bigger-110"></i>
            <spring:message code="label.campaign.back_to_campaign_list"/>
        </button>
    </div>
</div>
<!-- /widget-body -->
<script type="text/javascript">
    jQuery(function ($) {
        $('.easy-pie-chart.percentage').each(function () {
            var $box = $(this).closest('.infobox');
            var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
            var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
            var size = parseInt($(this).data('size')) || 50;
            $(this).easyPieChart({
                barColor: barColor,
                trackColor: trackColor,
                scaleColor: false,
                lineCap: 'butt',
                lineWidth: parseInt(size / 10),
                animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
                size: size
            });
        })

        $('.sparkline').each(function () {
            var $box = $(this).closest('.infobox');
            var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
            $(this).sparkline('html', {tagValuesAttribute: 'data-values', type: 'bar', barColor: barColor, chartRangeMin: $(this).data('min') || 0});
        });


        var placeholder = $('#piechart-placeholder').css({'width': '90%', 'min-height': '150px'});
        var data = [
            { label: "Mobile", data: 40, color: "#2091CF"},
            { label: "Desktop", data: 45, color: "#68BC31"},
            { label: "Other", data: 5, color: "#DA5430"}
        ]

        function drawPieChart(placeholder, data, position) {
            $.plot(placeholder, data, {
                series: {
                    pie: {
                        show: true,
                        tilt: 0.8,
                        highlight: {
                            opacity: 0.25
                        },
                        stroke: {
                            color: '#fff',
                            width: 2
                        },
                        startAngle: 2
                    }
                },
                legend: {
                    show: true,
                    position: position || "ne",
                    labelBoxBorderColor: null,
                    margin: [-30, 15]
                },
                grid: {
                    hoverable: true,
                    clickable: true
                }
            })
        }

        drawPieChart(placeholder, data);

        /**
         we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
         so that's not needed actually.
         */
        placeholder.data('chart', data);
        placeholder.data('draw', drawPieChart);


        var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
        var previousPoint = null;

        placeholder.on('plothover', function (event, pos, item) {
            if (item) {
                if (previousPoint != item.seriesIndex) {
                    previousPoint = item.seriesIndex;
                    var tip = item.series['label'] + " : " + item.series['percent'] + '%';
                    $tooltip.show().children(0).text(tip);
                }
                $tooltip.css({top: pos.pageY + 10, left: pos.pageX + 10});
            } else {
                $tooltip.hide();
                previousPoint = null;
            }

        });


        var d1 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.5) {
            d1.push([i, Math.sin(i)]);
        }

        var d2 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.5) {
            d2.push([i, Math.cos(i)]);
        }

        var d3 = [];
        for (var i = 0; i < Math.PI * 2; i += 0.2) {
            d3.push([i, Math.tan(i)]);
        }


        var sales_charts = $('#sales-charts').css({'width': '100%', 'height': '220px'});
        $.plot("#sales-charts", [
            { label: "Domains", data: d1 },
            { label: "Hosting", data: d2 },
            { label: "Services", data: d3 }
        ], {
            hoverable: true,
            shadowSize: 0,
            series: {
                lines: { show: true },
                points: { show: true }
            },
            xaxis: {
                tickLength: 0
            },
            yaxis: {
                ticks: 10,
                min: -2,
                max: 2,
                tickDecimals: 3
            },
            grid: {
                backgroundColor: { colors: [ "#fff", "#fff" ] },
                borderWidth: 1,
                borderColor: '#555'
            }
        });


        $('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});
        function tooltip_placement(context, source) {
            var $source = $(source);
            var $parent = $source.closest('.tab-content')
            var off1 = $parent.offset();
            var w1 = $parent.width();

            var off2 = $source.offset();
            var w2 = $source.width();

            if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2)) return 'right';
            return 'left';
        }


        $('.dialogs,.comments').slimScroll({
            height: '300px'
        });


        //Android's default browser somehow is confused when tapping on label which will lead to dragging the task
        //so disable dragging when clicking on label
        var agent = navigator.userAgent.toLowerCase();
        if ("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))
            $('#tasks').on('touchstart', function (e) {
                var li = $(e.target).closest('#tasks li');
                if (li.length == 0)return;
                var label = li.find('label.inline').get(0);
                if (label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation();
            });

        $('#tasks').sortable({
                    opacity: 0.8,
                    revert: true,
                    forceHelperSize: true,
                    placeholder: 'draggable-placeholder',
                    forcePlaceholderSize: true,
                    tolerance: 'pointer',
                    stop: function (event, ui) {//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
                        $(ui.item).css('z-index', 'auto');
                    }
                }
        );
        $('#tasks').disableSelection();
        $('#tasks input:checkbox').removeAttr('checked').on('click', function () {
            if (this.checked) $(this).closest('li').addClass('selected');
            else $(this).closest('li').removeClass('selected');
        });


    })
    function backToCampaignList() {

        window.location.replace("${pageContext.request.contextPath}/main/merchant/campaign_management/1");
    }
</script>