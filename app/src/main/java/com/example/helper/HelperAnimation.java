package com.example.helper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class HelperAnimation {
    private final View head, body, eyeL,eyeR;
    private final TextView textView;
    public AnimatorSet mouth = new AnimatorSet();

    public HelperAnimation(View head, View body, View eyeL, View eyeR, TextView textViewOfHelper) {
        this.head = head;
        this.body = body;
        this.eyeL = eyeL;
        this.eyeR = eyeR;
        this.textView = textViewOfHelper;
    }
    public void speechAnimation(String[] dialogMassive) {

        ObjectAnimator headUp = ObjectAnimator.ofFloat(head,"translationY",-10f);
        ObjectAnimator rightEyeUp = ObjectAnimator.ofFloat(eyeR, "translationY",-10f);
        ObjectAnimator leftEyeUp = ObjectAnimator.ofFloat(eyeL, "translationY",-10f);
        ObjectAnimator bodyUp = ObjectAnimator.ofFloat(body,"translationY",-5f);

        ObjectAnimator headDown = ObjectAnimator.ofFloat(head,"translationY",10);
        ObjectAnimator rightEyeDown = ObjectAnimator.ofFloat(eyeR,"translationY",10);
        ObjectAnimator leftEyeDown = ObjectAnimator.ofFloat(eyeL,"translationY",10);
        ObjectAnimator headRotation = ObjectAnimator.ofFloat(head,"rotation", 5);
        ObjectAnimator rightEyeRotation = ObjectAnimator.ofFloat(eyeR,"rotation",5);
        ObjectAnimator leftEyeRotation = ObjectAnimator.ofFloat(eyeL,"rotation",5);
        ObjectAnimator bodyDown = ObjectAnimator.ofFloat(body, "translationY",5);

        ObjectAnimator headRotationL = ObjectAnimator.ofFloat(head, "rotation", -5);
        ObjectAnimator rightEyeRotationL = ObjectAnimator.ofFloat(eyeR,"rotation",-5f);
        ObjectAnimator leftEyeRotationL = ObjectAnimator.ofFloat(eyeL,"rotation",-5f);

        ObjectAnimator normalRotationHead = ObjectAnimator.ofFloat(head, "rotation", 0);
        ObjectAnimator normalRotationEyeL = ObjectAnimator.ofFloat(eyeL, "rotation", 0);
        ObjectAnimator normalRotationEyeR = ObjectAnimator.ofFloat(eyeR, "rotation", 0);
        mouth.setDuration(200);

        mouth.play(headUp).with(rightEyeUp).with(leftEyeUp).with(bodyUp);
        mouth.play(headDown).with(rightEyeDown).with(leftEyeDown)
                    .with(bodyDown).with(headRotation)
                    .with(rightEyeRotation).with(leftEyeRotation)
                    .after(headUp);
        mouth.play(headRotationL).with(rightEyeRotationL).with(leftEyeRotationL).after(headDown);
        mouth.play(normalRotationHead).with(normalRotationEyeL).with(normalRotationEyeR)
                    .after(headRotationL);
        mouth.addListener(new AnimatorListenerAdapter() {
            public int numberOfStartedAnim;
                @SuppressLint("SetTextI18n")
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    if (textView.getText() != null) {
                        Log.d("numberSTART",numberOfStartedAnim + "");
                        textView.setText(textView.getText() + " " + dialogMassive[numberOfStartedAnim]);
                    } else {
                        textView.setText(dialogMassive[numberOfStartedAnim]);
                    }
                    numberOfStartedAnim = numberOfStartedAnim + 1;
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if(numberOfStartedAnim < dialogMassive.length) {
                        Log.d("numberEND",numberOfStartedAnim + "");
                        mouth.start();
                    }
                }
        });
        mouth.start();

    };
    public void speechWithAngry(String[] speechText) {

        ObjectAnimator headUp = ObjectAnimator.ofFloat(head,"translationY",-10f);
        ObjectAnimator rightEyeUp = ObjectAnimator.ofFloat(eyeR, "translationY",-10f);
        ObjectAnimator leftEyeUp = ObjectAnimator.ofFloat(eyeL, "translationY",-10f);
        ObjectAnimator bodyUp = ObjectAnimator.ofFloat(body,"translationY",-5f);

        ObjectAnimator headDown = ObjectAnimator.ofFloat(head,"translationY",10);
        ObjectAnimator rightEyeDown = ObjectAnimator.ofFloat(eyeR,"translationY",10);
        ObjectAnimator leftEyeDown = ObjectAnimator.ofFloat(eyeL,"translationY",10);
        ObjectAnimator headRotation = ObjectAnimator.ofFloat(head,"rotation", 5);
        ObjectAnimator rightEyeRotation = ObjectAnimator.ofFloat(eyeR,"rotation",-5);
        ObjectAnimator leftEyeRotation = ObjectAnimator.ofFloat(eyeL,"rotation",5);
        ObjectAnimator bodyDown = ObjectAnimator.ofFloat(body, "translationY",5);

        ObjectAnimator headRotationL = ObjectAnimator.ofFloat(head, "rotation", -5);
        ObjectAnimator rightEyeRotationL = ObjectAnimator.ofFloat(eyeR,"rotation",-5f);
        ObjectAnimator leftEyeRotationL = ObjectAnimator.ofFloat(eyeL,"rotation",-5f);

        ObjectAnimator normalRotationHead = ObjectAnimator.ofFloat(head, "rotation", 0);
        ObjectAnimator normalRotationEyeL = ObjectAnimator.ofFloat(eyeL, "rotation", 0);
        ObjectAnimator normalRotationEyeR = ObjectAnimator.ofFloat(eyeR, "rotation", 0);
        mouth.setDuration(200);

        mouth.play(headUp).with(rightEyeUp).with(leftEyeUp).with(bodyUp);
        mouth.play(headDown).with(rightEyeDown).with(leftEyeDown)
                .with(bodyDown).with(headRotation)
                .with(rightEyeRotation).with(leftEyeRotation)
                .after(headUp);
        mouth.play(headRotationL).with(rightEyeRotationL).with(leftEyeRotationL).after(headDown);
        mouth.play(normalRotationHead).with(normalRotationEyeL).with(normalRotationEyeR)
                .after(headRotationL);
        mouth.addListener(new AnimatorListenerAdapter() {
            public int numberOfStartedAnim;
            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (textView.getText() != null) {
                    Log.d("numberSTART",numberOfStartedAnim + "");
                    textView.setText(textView.getText() + " " + speechText[numberOfStartedAnim]);
                } else {
                    textView.setText(speechText[numberOfStartedAnim]);
                }
                numberOfStartedAnim = numberOfStartedAnim + 1;
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(numberOfStartedAnim < speechText.length) {
                    Log.d("numberEND",numberOfStartedAnim + "");
                    mouth.start();
                }
            }
        });
        mouth.start();

    }
    public void speechWithCrazy(String[] speechText) {}
}
