package elementar.matematica.pedrock.matemticaelementar;

import android.animation.Animator;
import android.content.res.Configuration;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class LottieController {

    // Método que inicia a Animação Lottie (apenas no MainActivity)
    public static void startLottieAnimation(LottieAnimationView animationView, int id, String jsonFile, float speed, int loops) {
        animationView.setAnimation(jsonFile);
        animationView.setSpeed(speed);
        animationView.setRepeatCount(loops);
        animationView.playAnimation();
    }

    // Método que inicia a Animação Lottie
    public static void startLottieAnimation(View view, LottieAnimationView animationView, int id, String jsonFile, float speed, int loops) {
        animationView = view.findViewById(id);
        animationView.setAnimation(jsonFile);
        animationView.setSpeed(speed);
        animationView.setRepeatCount(loops);
        animationView.playAnimation();
    }

    // Método com listener para cancelar animação ao acabar (libera memória RAM)
    public static void cancelLottieAnimation(final LottieAnimationView animationView) {
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.cancelAnimation();
                animationView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    // Modifica a visibilidade da animação ao girar tela (corrige bug de animação aparecer sozinha)
    public static void changeAnimationVisibility(View view, LottieAnimationView animationView, int id, int currentOrientation) {
        animationView = view.findViewById(id);

        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            animationView.setVisibility(View.INVISIBLE);

        } else {
            animationView.setVisibility(View.INVISIBLE);
        }
    }

}
