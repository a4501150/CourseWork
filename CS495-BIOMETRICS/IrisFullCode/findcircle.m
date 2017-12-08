function [row, col, r] = findcircle(image,lradius,uradius,scaling, sigma, hithres, lowthres, vert, horz)

lradsc = round(lradius*scaling);
uradsc = round(uradius*scaling);
rd = round(uradius*scaling - lradius*scaling);

% generate the edge image
[I2 or] = canny(image, sigma, scaling, vert, horz);
I3 = adjgamma(I2, 1.9);
I4 = nonmaxsup(I3, or, 1.5);
edgeimage = hysthresh(I4, hithres, lowthres);

% perform the circular Hough transform
h = houghcircle(edgeimage, lradsc, uradsc);

maxtotal = 0;

% find the maximum in the Hough space, and hence
% the parameters of the circle
for i=1:rd

    layer = h(:,:,i);
    [maxlayer] = max(max(layer));


    if maxlayer > maxtotal

        maxtotal = maxlayer;


        r = int32((lradsc+i) / scaling);

        [row,col] = ( find(layer == maxlayer) );


        row = int32(row(1) / scaling); % returns only first max value
        col = int32(col(1) / scaling);

    end

end