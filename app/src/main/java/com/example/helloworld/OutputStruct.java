package com.example.helloworld;

public class OutputStruct {
    int[] image_data;
    double left_percentage, right_percentage, top_percentage, bottom_percentage;
    double x_percentage, y_percentage;
    int status;

    int[] output_image;
    int output_width, output_height;
    boolean furry;
    boolean corner;

    double furry_percentage;
    int bad_corners;

    int nof_cards;

    int left_line;
    int right_line;
    int top_line;
    int bottom_line;

    int outer_left;
    int outer_right;
    int outer_top;
    int outer_bottom;

    OutputStruct(){
        left_percentage = 0.0;
        right_percentage = 0.0;
        top_percentage = 0.0;
        bottom_percentage = 0.0;
        x_percentage = 0.0;
        y_percentage = 0.0;
        status = 0;

        output_height = 0;
        output_width = 0;
        furry = false;
        corner = false;
        nof_cards = 0;

        furry_percentage = 0.0;
        bad_corners = 0;

        left_line = 0;
        right_line = 0;
        top_line = 0;
        bottom_line = 0;

        outer_left = 20;
        outer_right = 770;
        outer_top = 20;
        outer_bottom = 1070;
    }
}

