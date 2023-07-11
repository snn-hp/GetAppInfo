package  com.yumu.appinfo.transform;


import static  com.yumu.appinfo.transform.TransformerStyle.ACCORDION;
import static  com.yumu.appinfo.transform.TransformerStyle.DEPTH;
import static  com.yumu.appinfo.transform.TransformerStyle.ROTATE;
import static  com.yumu.appinfo.transform.TransformerStyle.SCALE_IN;
import static  com.yumu.appinfo.transform.TransformerStyle.STACK;

import androidx.viewpager2.widget.ViewPager2;

public class PageTransformerFactory {

    public ViewPager2.PageTransformer createPageTransformer(int transformerStyle) {
        ViewPager2.PageTransformer transformer = null;
        switch (transformerStyle) {
            case DEPTH:
                transformer = new DepthPageTransformer();
                break;
            case ROTATE:
                transformer = new RotateUpTransformer();
                break;
            case STACK:
                transformer = new StackTransformer();
                break;
            case ACCORDION:
                transformer = new AccordionTransformer();
                break;
            case SCALE_IN:
                transformer = new ScaleInTransformer(ScaleInTransformer.DEFAULT_MIN_SCALE);
                break;
        }
        return transformer;
    }
}
