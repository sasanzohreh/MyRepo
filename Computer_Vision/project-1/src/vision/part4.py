#!/usr/bin/python3

import numpy as np


def my_conv2d_freq(image: np.ndarray, filter: np.ndarray) -> np.ndarray:
    """
    Apply the Convolution Theorem to perform the convolution operation. 
    
    Return 
    - the input image represented in the frequency domain, 
    - the filter represented in the frequency domain,
    - the result of the convolution in the frequency domain, and 
    - the result of the convolution in the spatial domain.

    We will plot and analyze these to gain a better understanding of what is going on.

    Args:
        image: array of shape (m, n)
        filter: array of shape (k, j)
    Returns:
        image_freq: array of shape (m, n)
        filter_freq: array of shape (m, n)
        conv_result_freq: array of shape (m, n)
        conv_result: array of shape (m, n)
    HINTS:
    - Pad your filter in the spatial domain. We want to retain all of the high frequencies after the FFT
    - Return only the real component of the convolution result
    - Numpy considers frequency graphs differently than we have shown them in class. Look into the 
      documentation for np.fft.fft2 for how to account for this in the output image.
    - When applying padding, only use the zero-padding method.
    """

    ############################
    ### TODO: YOUR CODE HERE ###

    image_freq = np.fft.fft2(image)
    padR = (int)((image.shape[0] - filter.shape[0])/2)
    padC = (int)((image.shape[1] - filter.shape[1])/2)
    padRr = padR + 0
    padCr = padC + 0
    if (padR + padRr + filter.shape[0]) != image.shape[0]:
        padRr += 1
    if (padC + padCr + filter.shape[1]) != image.shape[1]:
        padCr += 1

    filter = np.pad(filter, ((padR, padRr), (padC, padCr)),'constant', constant_values=0)
    filter_freq = np.fft.fft2(filter)
    #filter_freq = np.fft.fft2(filter, s=(((int)(image.shape[0])), ((int)(image.shape[1]))))
    conv_result_freq = np.multiply(image_freq, filter_freq)
    conv_result = np.real(np.fft.ifftshift(np.fft.ifft2(conv_result_freq)))

    ### END OF STUDENT CODE ####
    ############################

    return image_freq, filter_freq, conv_result_freq, conv_result 


def my_deconv2d_freq(image: np.ndarray, filter: np.ndarray) -> np.ndarray:
    """
    Apply the Convolution Theorem to perform the convolution operation.
    
    Return 
    - the input image represented in the frequency domain, 
    - the filter represented in the frequency domain,
    - the result of the deconvolution in the frequency domain, and 
    - the result of the deconvolution in the spatial domain.

    We will plot and analyze these to gain a better understanding of what is going on.

    Args:
        image: array of shape (m, n)
        filter: array of shape (k, j)
    Returns:
        image_freq: array of shape (m, n)
        filter_freq: array of shape (m, n)
        deconv_result_freq: array of shape (m, n)
        deconv_result: array of shape (m, n)
    HINTS:
    - Pad your filter in the spatial domain. We want to retain all of the high frequencies after the FFT
    - Return only the real component of the deconvolution result
    - Numpy considers frequency graphs differently than we have shown them in class. Look into the 
      documentation for np.fft.fft2 to see what this means and to account for this in the output image.
    - When applying padding, only use the zero-padding method.
    """

    ############################
    ### TODO: YOUR CODE HERE ###

    image_freq = np.fft.fft2(image)
    padR = (int)((image.shape[0] - filter.shape[0])/2)
    padC = (int)((image.shape[1] - filter.shape[1])/2)
    padRr = padR + 0
    padCr = padC + 0
    if (padR + padRr + filter.shape[0]) != image.shape[0]:
        padRr += 1
    if (padC + padCr + filter.shape[1]) != image.shape[1]:
        padCr += 1

    filter = np.pad(filter, ((padR, padRr), (padC, padCr)),'constant', constant_values=0)
    filter_freq = np.fft.fft2(filter)
    #filter_freq = np.fft.fft2(filter, s=(((int)(image.shape[0])), ((int)(image.shape[1]))))
    deconv_result_freq = np.divide(image_freq, filter_freq)
    deconv_result = np.real(np.fft.ifftshift(np.fft.ifft2(deconv_result_freq)))

    ### END OF STUDENT CODE ####
    ############################

    return image_freq, filter_freq, deconv_result_freq, deconv_result





