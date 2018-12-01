package co.codemaestro.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class EditTextWithClear extends android.support.v7.widget.AppCompatEditText {
    Drawable clearButtonImage;


    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((getCompoundDrawablesRelative()[2] != null)) {
                    float clearButtonStart;
                    float clearButtonEnd;
                    boolean isClearButtonClicked = false;

                    // Detech the touch in RTL or LTR layout direction
                    if(getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        clearButtonEnd = clearButtonImage.getIntrinsicWidth() + getPaddingStart();
                        if(event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }

                    } else {
                        clearButtonStart = (getWidth() - getPaddingEnd() - clearButtonImage.getIntrinsicWidth());
                        if(event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }

                    // Check for actions if the button is tapped
                    if(isClearButtonClicked) {
                        // Check for action down
                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }
                        // Check for action up
                        if(event.getAction() == MotionEvent.ACTION_UP) {
                            clearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    } else {
                        return false;
                    }
                }


                return false;
            }
        });
    }



    //Shows the clear X Button
    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonImage, null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}
