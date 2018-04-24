package vn.edu.hust.soict.khacsan.myapp.svgwriter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;


import vn.edu.hust.soict.khacsan.myapp.R;

public class SVGCanvasView extends View {

    private ArrayList<Path> pathArrayList;
    private boolean isRunning = false;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath,mPathPencil;
    Context context;
    private Paint mPaint, pencilPaint, textPaint;
    long startTime;
    int numStroke = 0;
    private boolean stop;
    private PathMeasure measure;
    private float distance, speed;
    private float pos[], tan[];
    private int scaleNumber;
    private int currentWidth, currentHeight;
    private ArrayList<String> colorPath;
    private ArrayList<Integer> ramdomNumber;
    private float paddingTop,paddingLeft;
    private Matrix scaleMatrix;
    private float mX,mY;
    private static final int TOLERANCE = 5;
    private SvgPathParser pathParser;

    public SVGCanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        stop = false;
        context = c;
        startTime = System.currentTimeMillis();
        this.postInvalidate();
        pathParser = new SvgPathParser();
        // we set a new Path
        mPath = new Path();
        pathArrayList = new ArrayList<Path>();
        // and we set a new Paint with the desired attributes
        //todo paint
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#3367D6"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(16f);
        measure = new PathMeasure();
        distance = 0;
        speed = 0;
        pos = new float[2];
        tan = new float[2];
        scaleMatrix = new Matrix();
        //todo pencil
        mPathPencil = new Path();
        pencilPaint = new Paint();
        pencilPaint.setAntiAlias(true);
        pencilPaint.setDither(true);
        pencilPaint.setColor(Color.parseColor("#8b7355"));
        pencilPaint.setStyle(Paint.Style.STROKE);
        pencilPaint.setStrokeJoin(Paint.Join.ROUND);
        pencilPaint.setStrokeWidth(8f);
        // todo number
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(Color.parseColor("#D73F3F"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeWidth(1f);
        textPaint.setTextSize(24f);
        pathArrayList = new ArrayList<>();

        paddingLeft = -1;
        paddingTop = -1;
    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        currentWidth = w;
        currentHeight = h;
        scaleNumber = w / 110;
//        Log.d("Scale number onsize change", Integer.toString(scaleNumber));
    }

    // override onDraw
    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        if(stop){
//            for (int i = 0; i < pathArrayList.size(); i++) {
//                canvas.drawPath(pathArrayList.get(i), mPaint);
//                mPaint.setColor(Color.parseColor(colorPath.get(i)));
//            }
//            canvas.drawPath(mPathPencil,pencilPaint);
//        }

//        if (!isScalePath) {
////            for (Path path : pathArrayList) {
////                path.transform(scaleMatrix);
////            }
////            for (TextPoint textPoint : textPointArrayList) {
////                textPoint.setX(textPoint.getX() * scaleUnit);
////                textPoint.setY(textPoint.getY() * scaleUnit);
////            }
////            textPaint.setTextSize(scaleUnit * 6);
////            textPaint.setTypeface(Typeface.SANS_SERIF);
////            mPaint.setStrokeWidth(scaleUnitX);
////            pencilPaint.setStrokeWidth(scaleUnitX);
//            isScalePath = true;
//        }
        canvas.drawBitmap(mBitmap, 0f, 0f, new Paint(4));
        if (isRunning && !stop) {
            if (numStroke >= pathArrayList.size()) {
                mPath.reset();
                numStroke = 0;
                stop = true;
            }
            measure.setPath(pathArrayList.get(numStroke), false);
            if (distance < measure.getLength()) {
                measure.getPosTan(distance, pos, tan);
                speed = measure.getLength() / 10;
                if (distance == 0) {
                    canvas.drawText(Integer.toString(numStroke + 1), pos[0] + paddingLeft - 30, pos[1] +paddingTop, textPaint);
                    mCanvas.drawText(Integer.toString(numStroke + 1), pos[0] + paddingLeft - 30, pos[1] +paddingTop, textPaint);
                    mPath.moveTo(pos[0] + paddingLeft, pos[1] +paddingTop);
                }
                // todo speed
                distance += 8f;

                mPath.lineTo(pos[0] + paddingLeft, pos[1]+ paddingTop);
                mPaint.setColor(Color.parseColor(colorPath.get(ramdomNumber.get(numStroke))));

                canvas.drawPath(mPath, mPaint);

//                for (int i = 0; i <= numStroke; i++) {
//                    //  canvas.drawText(Integer.toString(i + 1), textPointArrayList.get(i).getX(), textPointArrayList.get(i).getY(), textPaint);
//                }
            } else {
//                for (int i = 0; i <= numStroke; i++) {
//                    /*canvas.drawText(Integer.toString(i + 1), textPointArrayList.get(i).getX(), textPointArrayList.get(i).getY(), textPaint);
//                    canvas.drawPath(mPath, mPaint);*/
//                }
                mCanvas.drawPath(mPath, mPaint);
                distance = 0;
                numStroke++;
                mPath.reset();
                textPaint.setColor(Color.parseColor(colorPath.get(ramdomNumber.get(numStroke - 1))));
            }
            invalidate();
        }
    }

    public void setListPath(ArrayList<String> listPath) {
        isRunning = true;
        for (String str: listPath) {
            try {
                Path path = pathParser.createPathFromPathData(str);
                // todo matrix
                scaleMatrix.setScale(3f, 3f);
                if (path != null) {
                    path.transform(scaleMatrix);
                    pathArrayList.add(path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    public void nodeTravel(Node node) {
//        if (node.hasChildNodes()) {
//            NodeList nodeList = node.getChildNodes();
//            for (int i = 0; i < nodeList.getLength(); i++) {
//                nodeTravel(nodeList.item(i));
//            }
//        } else {
//            Element element = (Element) node;
//            listPath.add(element.getAttribute("d"));
//        }
//
//    }
//
//    public void textTravel(Node node) {
//        NodeList nodeList = node.getChildNodes();
//        for (int i = 0; i < nodeList.getLength(); i++) {
//            try {
//                Element element = (Element) nodeList.item(i);
//                textPointArrayList.add(stringToTextPoint(element.getAttribute("transform")));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

//    public TextPoint stringToTextPoint(String string) {
//        String sub1 = string.replace("matrix(1 0 0 1 ", "");
//        String sub2 = sub1.replace(")", "");
//        String sub3 = sub2.substring(0, sub2.lastIndexOf(" "));
//        String sub4 = sub2.substring(sub2.lastIndexOf(" ") + 1, sub2.length());
//        //todo number scale
//        return new TextPoint(Float.parseFloat(sub3), Float.parseFloat(sub4));
//    }

    public void init(ArrayList<String> listPathString) {
        isRunning = true;

        setListPath(listPathString);

//        InputStream stream = null;
//        try {
//            stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder;
//
//        try {
//            builder = fac.newDocumentBuilder();
//            Document doc = builder.parse(stream);
//            Element root = doc.getDocumentElement();
//            NodeList list = root.getChildNodes();
//            Node node = list.item(0);
//            Node textNode = list.item(1);
//            textTravel(textNode);
//            nodeTravel(node);
//
//            SVGParser pathParser = new SVGParser();
//            for (int i = 0; i < listPath.size(); i++) {
//                Path path = pathParser.parsePath(listPath.get(i));
//                /*
//                scaleMatrix.setScale(4f, 4f);
//                Log.d("Scale Number", Integer.toString(scaleNumber));
//                path.transform(scaleMatrix);*/
//                pathArrayList.add(path);
//            }
//
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        resolveColor();
    }

//    public void init(String data, int numberScale) {
//        isRunning = true;
//        invalidate();
//        InputStream stream = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
//        }
//        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
//        DocumentBuilder builder;
//
//        try {
//            builder = fac.newDocumentBuilder();
//            Document doc = builder.parse(stream);
//            Element root = doc.getDocumentElement();
//            NodeList list = root.getChildNodes();
//            Node node = list.item(0);
//            Node textNode = list.item(1);
//            textTravel(textNode);
//            nodeTravel(node);
//
//            for (int i = 0; i < listPath.size(); i++) {
//                Path path = PathParser.createPathFromPathData(listPath.get(i));
//
//                scaleMatrix.setScale(4f, 4f);
//                if (path != null) {
//                    path.transform(scaleMatrix);
//                    pathArrayList.add(path);
//                }
//            }
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        resolveColor();
//    }

    public void resolveColor() {
        colorPath = new ArrayList<>();
        colorPath.add(context.getResources().getString(R.string.color_1));
        colorPath.add(context.getResources().getString(R.string.color_2));
        colorPath.add(context.getResources().getString(R.string.color_3));
        colorPath.add(context.getResources().getString(R.string.color_4));
        colorPath.add(context.getResources().getString(R.string.color_5));
        colorPath.add(context.getResources().getString(R.string.color_6));
        colorPath.add(context.getResources().getString(R.string.color_7));
        colorPath.add(context.getResources().getString(R.string.color_8));
        colorPath.add(context.getResources().getString(R.string.color_9));
        colorPath.add(context.getResources().getString(R.string.color_10));
        colorPath.add(context.getResources().getString(R.string.color_11));
        colorPath.add(context.getResources().getString(R.string.color_12));
        colorPath.add(context.getResources().getString(R.string.color_13));
        colorPath.add(context.getResources().getString(R.string.color_14));
        colorPath.add(context.getResources().getString(R.string.color_15));
        colorPath.add(context.getResources().getString(R.string.color_16));
        colorPath.add(context.getResources().getString(R.string.color_17));
        colorPath.add(context.getResources().getString(R.string.color_18));
        colorPath.add(context.getResources().getString(R.string.color_19));
        colorPath.add(context.getResources().getString(R.string.color_20));
        Random r = new Random();
        ramdomNumber = new ArrayList<>();
        for (int i = 0; i < pathArrayList.size(); i++) {
            if (i <= (colorPath.size() - 1)) {
                ramdomNumber.add(i);

            } else {
                ramdomNumber.add(i % colorPath.size());
            }
        }
        mPaint.setColor(Color.parseColor(colorPath.get(ramdomNumber.get(0))));
        textPaint.setColor(Color.parseColor(colorPath.get(ramdomNumber.get(0))));
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = 440;
        int desiredHeight = 440;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);

        if(paddingLeft < 0.0f || paddingTop < 0.0f) {
            this.paddingLeft = (width - 3*(pathParser.getxMax() - pathParser.getxMin()))/2;
            this.paddingTop = (height -  3*(pathParser.getyMax() - pathParser.getyMin())) / 3;
//            if(scaleUnitY > 4.5f) this.scaleUnitY = 4.5f;
//            this.scaleMatrix.setScale((int)this.scaleUnitX,this.scaleUnitY);
        }

    }


//    @SuppressLint("ClickableViewAccessibility")
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:{
//                mPathPencil.moveTo(x,y);
//                mX = x;
//                mY = y;
//                break;
//            }
//
//            case MotionEvent.ACTION_MOVE:{
//                float dx = Math.abs(x - mX);
//                float dy = Math.abs(y - mY);
//                if(dx >= TOLERANCE || dy >= TOLERANCE){
//                    mPathPencil.quadTo(mX,mY,(x+mX)/2, (y + mY)/2);
//                    mY = y;
//                    mX = x;
//                }
//                break;
//            }
//
//            case MotionEvent.ACTION_UP:{
//                mPathPencil.moveTo(mX,mY);
//                break;
//            }
//
//        }
//        invalidate();
//        return true;
//    }

    public void rePaint() {
        mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        distance = 0;
        numStroke = 0;
        mPath.reset();
//        mPathPencil.reset();
        stop = false;
        invalidate();
    }
}
