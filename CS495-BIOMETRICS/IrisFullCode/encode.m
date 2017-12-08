function [template, mask] = encode(polar_array,noise_array)

if exist('gabest_opt.mat')>0
    load gabest_opt beta;
    x         = beta;
    img       = polar_array;

    avgfilter = ones(size(x));
    avgfilter = avgfilter/sum(avgfilter);

    f1       = conv2(img,avgfilter,'same');
    f2       = conv2(f1-img,x,'same');
    template = f2>0;

    mask = noise_array;
else
    disp('File for GA based feature extraction is not available. Standard filtering will be performed.');
    mask   = noise_array;

    [N,M]  = size(polar_array);
    f      = ones(11,1);
    f      = f/sum(f(:));
    f      = f(:);
    avgval = zeros(N,M);
    for ii=1:N
        y  = polar_array(ii,:);
        y  = y(:);
        yf = conv2(y,f,'same');
        avgval(ii,:) = yf;
    end
    template = polar_array>avgval;
end