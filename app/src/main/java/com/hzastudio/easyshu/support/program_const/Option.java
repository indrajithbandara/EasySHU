package com.hzastudio.easyshu.support.program_const;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.adapter.CourseTimeRecyclerViewAdapter;
import com.hzastudio.easyshu.support.data_bean.CourseOption;
import com.hzastudio.easyshu.ui.widget.TextFloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

/**
 * 课程查询选项编程
 * @author Zean Huang
 * @link https://github.com/thunderbird1997
 */
public class Option {

    /*TODO:需要重点优化！*/
    /*TODO:用了比较傻的方式一个个选项写实现，以后考虑模块化实现*/

    private static final String[] TimeList = new String[]{"第1节","第2节","第3节","第4节","第5节","第6节","第7节","第8节","第9节","第10节","第11节","第12节","第13节"};
    private static final String[] DayList = new String[]{"星期一","星期二","星期三","星期四","星期五"};
    private static final String[] SimpleDayList = new String[]{"一","二","三","四","五"};

    public static final int Option_CourseCredit=0;
    private static CourseOption CourseCredit = new CourseOption(
            0,
            "学分",
            "---",
            "",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.option_value_with_suggestion, null);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .create();
                    dialog.show();
                    final EditText input = (EditText) view.findViewById(R.id.InputOptionValueWithSuggestion);
                    TextView hint = (TextView) view.findViewById(R.id.OptionValueWithSuggestionHint);
                    Button sug1 = (Button) view.findViewById(R.id.OptionValueSuggest1);
                    Button sug2 = (Button) view.findViewById(R.id.OptionValueSuggest2);
                    Button sug3 = (Button) view.findViewById(R.id.OptionValueSuggest3);
                    Button sug4 = (Button) view.findViewById(R.id.OptionValueSuggest4);
                    Button submit = (Button) view.findViewById(R.id.OptionValueWithSuggestionSubmit);
                    sug1.setText("1");
                    sug2.setText("2");
                    sug3.setText("3");
                    sug4.setText("4");
                    submit.setText("确定");
                    hint.setText("请输入学分");
                    sug1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TFAB.setTextToDraw("1");
                            dialog.dismiss();
                        }
                    });
                    sug2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TFAB.setTextToDraw("2");
                            dialog.dismiss();
                        }
                    });
                    sug3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TFAB.setTextToDraw("3");
                            dialog.dismiss();
                        }
                    });
                    sug4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            TFAB.setTextToDraw("4");
                            dialog.dismiss();
                        }
                    });
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String result = !input.getText().toString().equals("")
                                    ? input.getText().toString() : "---";
                            TFAB.setTextToDraw(result);
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view);
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                }
            }
    );

    public static final int Option_IsCourseFull=1;
    private static CourseOption IsCourseFull = new CourseOption(
            1,
            "尚未选满",
            "不指定",
            "false",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    String StatusString = TFAB.getTextToDraw();
                    /*循环*/
                    switch (StatusString) {
                        case "不指定":
                            TFAB.setTextToDraw("是");
                            break;
                        case "是":
                            TFAB.setTextToDraw("不指定");
                            break;
                    }
                }
            },
            new TextFloatingActionButton.OnTextToDrawChangedListener() {
                @Override
                public String onFormat(String newText) {
                    if(newText.equals("是"))return "true";
                    return "false";
                }
            }
    );

    public static final int Option_CourseTeacher=2;
    private static CourseOption CourseTeacher = new CourseOption(
            2,
            "教师名",
            "---",
            "",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.option_string, null);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .create();
                    dialog.show();
                    final EditText input = (EditText) view.findViewById(R.id.InputOptionString);
                    TextView hint = (TextView) view.findViewById(R.id.OptionStringHint);
                    Button submit = (Button) view.findViewById(R.id.OptionStringSubmit);
                    submit.setText("确定");
                    hint.setText("请输入教师名");
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String result = !input.getText().toString().equals("")
                                    ? input.getText().toString() : "---";
                            TFAB.setTextToDraw(result);
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view);
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    input.requestFocus();
                }
            }
    );

    public static final int Option_CourseTeacherNum=3;
    private static CourseOption CourseTeacherNum = new CourseOption(
            3,
            "教师号",
            "---",
            "",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.option_value, null);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .create();
                    dialog.show();
                    final EditText input = (EditText) view.findViewById(R.id.InputOptionValue);
                    TextView hint = (TextView) view.findViewById(R.id.OptionValueHint);
                    Button submit = (Button) view.findViewById(R.id.OptionValueSubmit);
                    submit.setText("确定");
                    hint.setText("请输入教师号");
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String result = !input.getText().toString().equals("")
                                    ? input.getText().toString() : "---";
                            TFAB.setTextToDraw(result);
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view);
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    input.requestFocus();
                }
            }
    );

    public static final int Option_CourseCampus=4;
    private static CourseOption CourseCampus = new CourseOption(
            4,
            "校区",
            "全部",
            String.valueOf(CourseStatus.DISTRICT_ALL),
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    String StatusString = TFAB.getTextToDraw();
                    /*循环*/
                    switch (StatusString) {
                        case "全部":
                            TFAB.setTextToDraw("本部");
                            break;
                        case "本部":
                            TFAB.setTextToDraw("嘉定");
                            break;
                        case "嘉定":
                            TFAB.setTextToDraw("徐泾");
                            break;
                        case "徐泾":
                            TFAB.setTextToDraw("宝山 东校区");
                            break;
                        case "宝山 东校区":
                            TFAB.setTextToDraw("全部");
                            break;
                    }
                }
            },
            new TextFloatingActionButton.OnTextToDrawChangedListener() {
                @Override
                public String onFormat(String newText) {
                    switch (newText)
                    {
                        case "全部":return String.valueOf(CourseStatus.DISTRICT_ALL);
                        case "本部":return String.valueOf(CourseStatus.DISTRICT_BEN_BU);
                        case "嘉定":return String.valueOf(CourseStatus.DISTRICT_JIA_DIN);
                        case "徐泾":return String.valueOf(CourseStatus.DISTRICT_XU_JING);
                        case "宝山 东校区":return String.valueOf(CourseStatus.DISTRICT_BAO_SHAN_DONG);
                        default:return String.valueOf(CourseStatus.DISTRICT_ALL);
                    }
                }
            }
    );

    public static final int Option_CourseTime=5;
    private static CourseOption CourseTime = new CourseOption(
            5,
            "上课时间",
            "---",
            "",
            new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.option_format_course_time, null);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .create();
                    dialog.show();
                    dialog.setContentView(view);
                    final List<String> CourseTimeList;

                    if (!Objects.equals(TFAB.getTextToDraw(), "---"))
                        CourseTimeList = Arrays.asList(TFAB.getTextToDraw().split(" "));
                    else CourseTimeList = new ArrayList<>();

                    final NumberPickerView day = view.findViewById(R.id.OptionCourseTimeDayPicker);
                    final NumberPickerView from = view.findViewById(R.id.OptionCourseTimeFromPicker);
                    final NumberPickerView to = view.findViewById(R.id.OptionCourseTimeToPicker);
                    from.setDisplayedValues(TimeList, true);
                    to.setDisplayedValues(TimeList, true);
                    day.setDisplayedValues(DayList, true);
                    from.setMinValue(0);from.setMaxValue(12);
                    to.setMinValue(0);to.setMaxValue(12);
                    day.setMinValue(0);day.setMaxValue(4);
                    from.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                            Log.d("sss","From value changed to:"+newVal);
                            to.smoothScrollToValue(newVal,false);
                        }
                    });
                    TextView hint = view.findViewById(R.id.OptionCourseTimeHint);
                    hint.setText("已选择的课程时间：");
                    Button submit = view.findViewById(R.id.OptionCourseTimeSubmit);
                    submit.setText("确定");
                    final RecyclerView TimeView = view.findViewById(R.id.OptionCourseTimeDisplayRecyclerView);
                    final CourseTimeRecyclerViewAdapter adapter = new CourseTimeRecyclerViewAdapter(CourseTimeList);
                    TimeView.setAdapter(new CourseTimeRecyclerViewAdapter(CourseTimeList));
                    LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    TimeView.setLayoutManager(manager);
                    TextFloatingActionButton button = view.findViewById(R.id.OptionCourseTimeAddButton);
                    button.setTextToDraw("添加");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("sss", "Day:" + day.getValue() + "\nFrom:" + from.getValue() + "\nTo:" + to.getValue());
                            String tmp = SimpleDayList[day.getValue()] +
                                    (from.getValue()+1) + "-" + (to.getValue()+1);
                            adapter.addItem(tmp);
                            TimeView.setAdapter(adapter);
                        }
                    });
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<String> CourseTimeList =((CourseTimeRecyclerViewAdapter)TimeView.getAdapter()).getList();
                            if(CourseTimeList.size()>0) {
                                StringBuilder builder = new StringBuilder();
                                for (int i = 0; i < CourseTimeList.size(); i++) {
                                    builder.append(CourseTimeList.get(i)).append(" ");
                                }
                                TFAB.setTextToDraw(builder.substring(0, builder.length() - 1));
                                dialog.dismiss();
                            }
                            else
                            {
                                TFAB.setTextToDraw("---");
                                Snackbar.make(v,"您未选取任何时间！",Snackbar.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
    );

    public static final int Option_CourseChooseNum=6;
    private static CourseOption CourseChooseNum = new CourseOption(
            6,
            "已选人数少于",
            "---",
            "",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.option_value, null);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .create();
                    dialog.show();
                    final EditText input = (EditText) view.findViewById(R.id.InputOptionValue);
                    TextView hint = (TextView) view.findViewById(R.id.OptionValueHint);
                    Button submit = (Button) view.findViewById(R.id.OptionValueSubmit);
                    submit.setText("确定");
                    hint.setText("请输入已选该课程人数的上限：");
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String result = !input.getText().toString().equals("")
                                    ? input.getText().toString() : "---";
                            TFAB.setTextToDraw(result);
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view);
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    input.requestFocus();
                }
            }
    );

    public static final int Option_CourseChooseNumField=7;
    private static CourseOption CourseChooseNumField = new CourseOption(
            7,
            "容量空余",
            "---",
            "",
            new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    final TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.option_field, null);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .create();
                    dialog.show();
                    final EditText inputStart = view.findViewById(R.id.InputOptionFieldStart);
                    final EditText inputEnd = view.findViewById(R.id.InputOptionFieldEnd);
                    TextView hint =  view.findViewById(R.id.OptionFieldHint);
                    Button submit = view.findViewById(R.id.OptionFieldSubmit);
                    submit.setText("确定");
                    hint.setText("请输入课程容量空余的范围");
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String start=inputStart.getText().toString();
                            String end=inputEnd.getText().toString();
                            if(!start.equals("") && !end.equals("")) {
                                if(Integer.parseInt(start)>Integer.parseInt(end))
                                {
                                    TFAB.setTextToDraw("---");
                                    Snackbar.make(v,"上限值不能小于下限值！",Snackbar.LENGTH_SHORT).show();
                                }
                                else TFAB.setTextToDraw(start + "-" + end);
                            } else if(start.equals("") && !end.equals("")) {
                                TFAB.setTextToDraw("0-"+end);
                                Snackbar.make(v,"已自动将容量空余下限设为0！",Snackbar.LENGTH_SHORT).show();
                            }else if(!start.equals("") && end.equals("")) {
                                TFAB.setTextToDraw(start+"-"+start);
                                Snackbar.make(v,"已自动将容量空余上限设为下限值！",Snackbar.LENGTH_SHORT).show();
                            }
                            else{
                                TFAB.setTextToDraw("---");
                            }
                            dialog.dismiss();
                        }
                    });
                    dialog.setContentView(view);
                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    inputStart.requestFocus();
                }
            }
    );

    private static CourseOption[] mOptions = { CourseCredit,IsCourseFull,CourseTeacher,
            CourseTeacherNum,CourseCampus,CourseTime,CourseChooseNum,CourseChooseNumField};

    public static List<CourseOption> getCourseOption()
    {
        return Arrays.asList(mOptions);
    }


}
