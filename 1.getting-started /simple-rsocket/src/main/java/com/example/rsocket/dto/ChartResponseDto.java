package com.example.rsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChartResponseDto {
    private int input;
    private int output;

    private String getFormat(int value) {
        return "%3s|%" + value + "s";
    }

    @Override
    public String toString() {
        String chartFormat = this.getFormat(this.getOutput());
        return String.format(chartFormat, this.input, "X");
    }
}
