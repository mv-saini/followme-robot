package it.unicam.cs.followme.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestShapes {

    @Test
    void insideCircle(){
        Coordinates c2 = new Coordinates(0,1);
        Coordinates c3 = new Coordinates(0.5,0.5);
        Coordinates c4 = new Coordinates(3,2);
        Coordinates center = new Coordinates(0, 0);
        ShapeCircle circle = new ShapeCircle( 1, "c1");
        assertTrue(circle.insideArea(c2, center));
        assertTrue(circle.insideArea(c3, center));
        assertFalse(circle.insideArea(c4, center));
    }

    @Test
    void insideCircle2(){
        Coordinates c1 = new Coordinates(5, 0);
        Coordinates c2 = new Coordinates(1.9447410490599, 8.925864315755);
        Coordinates c3 = new Coordinates(6.5, 7.5);
        Coordinates c4 = new Coordinates(4.1, 0.08);
        Coordinates c5 = new Coordinates(8.2956913186994, 8.7664763647564);
        Coordinates center = new Coordinates(5, 5);
        ShapeCircle circle = new ShapeCircle( 5, "c1");
        assertTrue(circle.insideArea(c1, center));
        assertTrue(circle.insideArea(c2, center));
        assertTrue(circle.insideArea(c3, center));
        assertFalse(circle.insideArea(c4, center));
        assertFalse(circle.insideArea(c5, center));
    }

    @Test
    void insideRectangle(){
        Coordinates c2 = new Coordinates(0,1);
        Coordinates c3 = new Coordinates(2.9980510652948, 0.4739137829455);
        Coordinates c4 = new Coordinates(3.5, 0.5);
        Coordinates center = new Coordinates(0, 0);
        ShapeRectangle r1 = new ShapeRectangle(6, 4, "r1");
        assertTrue(r1.insideArea(c2, center));
        assertTrue(r1.insideArea(c3, center));
        assertFalse(r1.insideArea(c4, center));
    }

    @Test
    void insideRectangle1(){
        Coordinates c1 = new Coordinates(4, -1);
        Coordinates c4 = new Coordinates(1, -4);
        Coordinates c3 = new Coordinates(4.9987793926369, -9.5012739831757);
        Coordinates c2 = new Coordinates(2.0106135514939, -4.5087396902249);
        Coordinates c5 = new Coordinates(6.0097527886954, -3.9995994914398);
        Coordinates center = new Coordinates(4, -5);
        ShapeRectangle r1 = new ShapeRectangle(4, 10, "r1");
        assertTrue(r1.insideArea(c1, center));
        assertTrue(r1.insideArea(c2, center));
        assertTrue(r1.insideArea(c3, center));
        assertFalse(r1.insideArea(c4, center));
        assertFalse(r1.insideArea(c5, center));
    }

}
