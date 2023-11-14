package ru.korotaev.libraryapp.dao;

import java.util.ArrayList;
import java.util.List;

public class CalculatePageNumbers {

    public static List<Integer> calculatePageNumbers(int currentPage, int totalPages) {
        int visiblePageCount = 5;
        List<Integer> pageNumbers = new ArrayList<>();

        int startPage = Math.max(0, currentPage - 2);
        int endPage = Math.min(totalPages - 1, startPage + visiblePageCount - 1);

        int additionalPagesLeft = Math.max(0, visiblePageCount - (endPage - startPage + 1));
        int additionalPagesRight = additionalPagesLeft;

        while (additionalPagesLeft > 0 && startPage > 0) {
            startPage--;
            additionalPagesLeft--;
        }

        while (additionalPagesRight > 0 && endPage < totalPages - 1) {
            endPage++;
            additionalPagesRight--;
        }

        for (int i = startPage; i <= endPage; i++) {
            pageNumbers.add(i);
        }

        return pageNumbers;
    }
}
