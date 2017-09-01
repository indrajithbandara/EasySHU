package com.hzastudio.easyshu.support.program_const;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hzastudio.easyshu.R;
import com.hzastudio.easyshu.support.data_bean.CourseOption;
import com.hzastudio.easyshu.ui.widget.TextFloatingActionButton;

import java.util.Arrays;
import java.util.List;

public class Option {

    /*TODO:需要重点优化！*/
    /*TODO:用了比较傻的方式一个个选项写实现，以后考虑模块化实现*/

    private static CourseOption CourseCredit = new CourseOption(
            "学分",
            "---",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    View view = LayoutInflater.from(v.getContext())
                            .inflate(R.layout.option_value_with_suggestion,null);
                    final AlertDialog dialog = new AlertDialog.Builder(v.getContext())
                            .create();
                    dialog.show();
                    final EditText input = (EditText) view.findViewById(R.id.InputOptionValueWithSuggestion);
                    TextView hint  = (TextView)view.findViewById(R.id.OptionValueWithSuggestionHint);
                    Button sug1 = (Button)view.findViewById(R.id.OptionValueSuggest1);
                    Button sug2 = (Button)view.findViewById(R.id.OptionValueSuggest2);
                    Button sug3 = (Button)view.findViewById(R.id.OptionValueSuggest3);
                    Button sug4 = (Button)view.findViewById(R.id.OptionValueSuggest4);
                    Button submit = (Button)view.findViewById(R.id.OptionValueWithSuggestionSubmit);
                    sug1.setText("1");sug2.setText("2");sug3.setText("3");sug4.setText("4");submit.setText("确定");hint.setText("请输入学分");
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

    private static CourseOption IsCourseFull = new CourseOption(
            "尚未选满",
            "---",
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextFloatingActionButton TFAB = (TextFloatingActionButton) v;
                    String StatusString = TFAB.getTextToDraw();
                    /*循环*/
                    if(StatusString.equals("---"))
                        TFAB.setTextToDraw("是");
                    else if(StatusString.equals("是"))
                        TFAB.setTextToDraw("否");
                    else if(StatusString.equals("否"))
                        TFAB.setTextToDraw("---");
                }
            }
    );

    private static CourseOption CourseTeacher = new CourseOption(
            "教师名",
            "---",
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

    private static CourseOption CourseTeacherNum = new CourseOption(
            "教师号",
            "---",
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

    private static CourseOption[] mOptions = { IsCourseFull,CourseCredit,CourseTeacher,CourseTeacherNum};

    public static List<CourseOption> getCourseOption()
    {
        return Arrays.asList(mOptions);
    }

}
