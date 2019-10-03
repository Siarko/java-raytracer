package pl.siarko.frame.components;

import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.lang.NumberFormatException;
import java.text.DecimalFormat;
import java.awt.event.KeyEvent;

import javax.swing.text.DefaultCaret;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Chad M Kilgore
 * Klasa elementu do wprowadzania liczb
 */
public class JNumberField extends javax.swing.JFormattedTextField {

    public void setIntegerOnly(boolean state){
        putClientProperty("integerOnly", state);
    }

    public boolean isIntegerOnly(){
        return getClientProperty("integerOnly") == Boolean.TRUE;
    }

    public boolean isPositiveOnly() {
        return getClientProperty("positiveOnly") == Boolean.TRUE;
    }

    public void setPositiveOnly(boolean state) {
        putClientProperty("positiveOnly", state);
    }

    /**
     * Creates a <code>JNumberField</code>. <code>DecimalFormat</code> is
     * wrapped in an appropriate <code>AbstractFormatter</code> which is
     * then wrapped in an <code>AbstractFormatterFactory</code>.
     */
    public JNumberField() {
        this.setPreferredSize(new Dimension(100, 24));
        DecimalFormat format = new DecimalFormat();
        format.setGroupingUsed(false);
        setFormatter(new javax.swing.text.NumberFormatter(format));
        setDocument(new NumberDocument());
    }

    /**
     * Creates a <code>JNumberField</code> with a
     * <code>DecimalFormat</code> and initial value.
     *
     * @param number Initial value to use
     */
    public JNumberField(Number number) {
        this();
        setText(number);
    }

    /**
     * Creates a <code>JNumberField</code> with a
     * <code>DecimalFormat</code> and initial value.
     *
     * @param value Initial value to use
     */
    public JNumberField(byte value) {
        this(new Byte(value));
    }

    /**
     * Creates a <code>JNumberField</code> with a
     * <code>DecimalFormat</code> and initial value.
     *
     * @param value Initial value to use
     */
    public JNumberField(double value) {
        this(new Double(value));
    }

    /**
     * Creates a <code>JNumberField</code> with a
     * <code>DecimalFormat</code> and initial value.
     *
     * @param value Initial value to use
     */
    public JNumberField(float value) {
        this(new Float(value));
    }

    /**
     * Creates a <code>JNumberField</code> with a
     * <code>DecimalFormat</code> and initial value.
     *
     * @param value Initial value to use
     */
    public JNumberField(int value) {
        this(new Integer(value));
    }

    /**
     * Creates a <code>JNumberField</code> with a
     * <code>DecimalFormat</code> and initial value.
     *
     * @param value Initial value to use
     */
    public JNumberField(long value) {
        this(new Long(value));
    }

    /**
     * Creates a <code>JNumberField</code> with a
     * <code>DecimalFormat</code> and initial value.
     *
     * @param value Initial value to use
     */
    public JNumberField(short value) {
        this(new Short(value));
    }

    @Override
    public void addActionListener(ActionListener a){
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if(Character.isDigit(e.getKeyChar()) ||
                        e.getKeyChar() == '.' ||
                        e.getKeyChar() == '-' ||
                        e.getKeyCode() == 8 || //backspace
                        e.getKeyCode() == 10 //enter
                        ){
                    a.actionPerformed(null);
                }
            }
        });
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified number. If the number is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When number has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * Threads
     * and Swing for more information.
     *
     * @param number the new number to be set
     * @see #getNumber
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(Number number) {
        setText(number.toString());
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified number. If the number is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When number has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * Threads
     * and Swing for more information.
     *
     * @param value the new number to be set
     * @see #byteValue
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(byte value) {
        setText(new Byte(value));
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified number. If the number is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When number has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * Threads
     * and Swing for more information.
     *
     * @param value the new number to be set
     * @see #doubleValue
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(double value) {
        setText(new Double(value));
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified number. If the number is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When number has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * Threads
     * and Swing for more information.
     *
     * @param value the new number to be set
     * @see #floatValue
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(float value) {
        setText(new Float(value));
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified number. If the number is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When number has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * Threads
     * and Swing for more information.
     *
     * @param value the new number to be set
     * @see #intValue
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(int value) {
        setText(new Integer(value));
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified number. If the number is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When number has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * Threads
     * and Swing for more information.
     *
     * @param value the new number to be set
     * @see #longValue
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(long value) {
        setText(new Long(value));
    }

    /**
     * Sets the text of this <code>TextComponent</code>
     * to the specified number. If the number is <code>null</code>
     * or empty, has the effect of simply deleting the old text.
     * When number has been inserted, the resulting caret location
     * is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * Threads
     * and Swing for more information.
     *
     * @param value the new number to be set
     * @see #shortValue
     * @see DefaultCaret
     * @beaninfo
     * description: the text of this component
     */
    public void setText(short value) {
        setText(new Short(value));
    }


    /**
     * Returns the <code>Number</code> contained in this <code>TextComponent</code>.
     * If the underlying document is empty,
     * will give a <code>NumberFormatException</code>.
     *
     * @return the number
     * @exception NumberFormatException if the document is empty
     * @see #setText
     */
    public Number getNumber()
            throws NumberFormatException {
        String str = getText();

        if( str == null || str.equals("") || str.equals("-"))
        {
            return 0;
        }

        if( str.indexOf('.') == -1 )
        { return getIntegerNumber(new BigInteger(str)); }

        return getDecimalNumber(new BigDecimal(str));
    }

    private Number getIntegerNumber(BigInteger big) {
        BigInteger MAX = new BigInteger(new Byte(Byte.MAX_VALUE).toString());
        BigInteger MIN = new BigInteger(new Byte(Byte.MIN_VALUE).toString());
        if( !(MIN.compareTo(big) > 0 || MAX.compareTo(big) < 0) )
        { return new Byte(big.byteValue()); }

        MAX = new BigInteger(new Short(Short.MAX_VALUE).toString());
        MIN = new BigInteger(new Short(Short.MIN_VALUE).toString());
        if( !(MIN.compareTo(big) > 0 || MAX.compareTo(big) < 0) )
        { return new Short(big.shortValue()); }

        MAX = new BigInteger(new Integer(Integer.MAX_VALUE).toString());
        MIN = new BigInteger(new Integer(Integer.MIN_VALUE).toString());
        if( !(MIN.compareTo(big) > 0 || MAX.compareTo(big) < 0) )
        { return new Integer(big.intValue()); }

        MAX = new BigInteger(new Long(Long.MAX_VALUE).toString());
        MIN = new BigInteger(new Long(Long.MIN_VALUE).toString());
        if( !(MIN.compareTo(big) > 0 || MAX.compareTo(big) < 0) )
        { return new Long(big.longValue()); }

        return big;
    }

    private Number getDecimalNumber(BigDecimal big) {
        BigDecimal MAX = new BigDecimal(new Float(Float.MAX_VALUE).toString());
        BigDecimal MIN = new BigDecimal(new Float(Float.MIN_VALUE).toString());
        if( !(MIN.compareTo(big) > 0 || MAX.compareTo(big) < 0) )
        { return new Float(big.floatValue()); }

        MAX = new BigDecimal(new Double(Double.MAX_VALUE).toString());
        MIN = new BigDecimal(new Double(Double.MIN_VALUE).toString());
        if( !(MIN.compareTo(big) > 0 || MAX.compareTo(big) < 0) )
        { return new Double(big.doubleValue()); }

        return big;
    }


    /**
     * Returns the <code>byte</code> contained in this <code>TextComponent</code>.
     * If the underlying document is empty,
     * will give a <code>NumberFormatException</code>.
     *
     * @return the number
     * @exception NumberFormatException if the document is empty
     * @see #setText
     */
    public byte byteValue()
            throws NumberFormatException {
        try
        { return getNumber().byteValue(); }
        catch( NumberFormatException e )
        { throw e; }
    }

    /**
     * Returns the <code>double</code> contained in this <code>TextComponent</code>.
     * If the underlying document is empty,
     * will give a <code>NumberFormatException</code>.
     *
     * @return the number
     * @exception NumberFormatException if the document is empty
     * @see #setText
     */
    public double doubleValue()
            throws NumberFormatException {
        try
        { return getNumber().doubleValue(); }
        catch( NumberFormatException e )
        { throw e; }
    }

    /**
     * Returns the <code>float</code> contained in this <code>TextComponent</code>.
     * If the underlying document is empty,
     * will give a <code>NumberFormatException</code>.
     *
     * @return the number
     * @exception NumberFormatException if the document is empty
     * @see #setText
     */
    public float floatValue()
            throws NumberFormatException {
        try
        { return getNumber().floatValue(); }
        catch( NumberFormatException e )
        { throw e; }
    }

    /**
     * Returns the <code>int</code> contained in this <code>TextComponent</code>.
     * If the underlying document is empty,
     * will give a <code>NumberFormatException</code>.
     *
     * @return the number
     * @exception NumberFormatException if the document is empty
     * @see #setText
     */
    public int intValue()
            throws NumberFormatException {
        try
        { return getNumber().intValue(); }
        catch( NumberFormatException e )
        { throw e; }
    }

    /**
     * Returns the <code>long</code> contained in this <code>TextComponent</code>.
     * If the underlying document is empty,
     * will give a <code>NumberFormatException</code>.
     *
     * @return the number
     * @exception NumberFormatException if the document is empty
     * @see #setText
     */
    public long longValue()
            throws NumberFormatException {
        try
        { return getNumber().longValue(); }
        catch( NumberFormatException e )
        { throw e; }
    }

    /**
     * Returns the <code>short</code> contained in this <code>TextComponent</code>.
     * If the underlying document is empty,
     * will give a <code>NumberFormatException</code>.
     *
     * @return the number
     * @exception NumberFormatException if the document is empty
     * @see #setText
     */
    public short shortValue()
            throws NumberFormatException {
        try
        { return getNumber().shortValue(); }
        catch( NumberFormatException e )
        { throw e; }
    }


    private class NumberDocument extends PlainDocument {
        public void insertString (int offset, String string, AttributeSet attributeSet)
                throws BadLocationException {
            String newString = "";
            String content = this.getText(0, this.getLength());

            for( int i=0; i < string.length(); i++ )
            {
                char ch = string.charAt(i);

                if(
                        Character.isDigit(ch) ||
                                (ch == '.' && content.indexOf('.') == -1 &&
                                        (offset > 0 || string.length() > 1)
                                        && !isIntegerOnly()
                                ) ||
                                (ch == '-' && content.indexOf('-') == -1 && offset == 0 && !isPositiveOnly())
                ) { newString += ch; }
            }

            super.insertString (offset, newString, attributeSet);
        }
    }
}