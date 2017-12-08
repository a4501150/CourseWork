function []=gairisrec()
% Genetic Algorithms for Iris Recognition
%
%
%
%
% FUNCTIONS
%
% Select image:                                  read the input image
%
% Add selected image to database:                the input image is added to database and will be used for training
%
% Database Info:                                 show informations about the images present in database.
%
% Iris Recognition:                              iris matching. The selected input image is processed
%                                                using pre-computed filter.
%
% GA Optimization:                               GA optimization for feature extraction
%
% Delete Database:                               remove database from the current directory
%
% Info:                                          show informations about this software
%
%
% Source code for Iris Recognition System:       how to obtain the complete source code
%
% Exit:                                          quit program
%
%
%  References:
%  Libor Masek, Peter Kovesi. MATLAB Source Code for a Biometric Identification
%  System Based on Iris Patterns. The School of Computer Science and Software Engineering,
%  The University of Western Australia. 2003.
%
%  The original source code is available at
%  http://www.csse.uwa.edu.au/~pk/studentprojects/libor/iriscode.zip
%
%  Libor Masek's dissertation is available at
%  http://www.csse.uwa.edu.au/~pk/studentprojects/libor/LiborMasekThesis.pdf
%
%  Michael B. Gordy, . "GA.M: A Matlab routine for function maximization
%  using a Genetic Algorithm". Code is available at
%  http://ideas.repec.org/e/pgo10.html
%
%  All tests were performed with CASIA Iris Image Database available at
%  http://www.sinobiometrics.com
%
%
%
%
%*****************************************************************
% Luigi Rosa
% Via Centrale 35
% 67042 Civita di Bagno
% L'Aquila - ITALY
% email  luigi.rosa@tiscali.it
% mobile +39 3207214179
% website http://www.advancedsourcecode.com
%*****************************************************************
%
%

%--------------------------------------------------------------------
clc;
chos=0;
possibility=9;

messaggio='Insert the number of class: each class determins a person. The ID number is a progressive, integer number. Each class should include a number of images for each person.';

while chos~=possibility,
    chos=menu('Genetic Algorithms for Iris Recognition','Select image','Add selected image to database','Database Info','Iris Recognition',...
        'GA Optimization','Delete Database','Program info',...
        'Source code','Exit');
    %----------------
    if chos==1,
        clc;
        [namefile,pathname]=uigetfile('*.*','Select image');
        if namefile~=0
            [img,map]=imread(strcat(pathname,namefile));
            imshow(img);
            dimensioni = size(img);
            if ndims(img)==3
                img = rgb2gray(img);
            end
        else
            warndlg('Input image must be selected.',' Warning ')
        end
        disp('An image has just been selected. Now you can add it to database (click on "Add selected image to database")');
        disp('or perform iris recognition (click on "Iris Recognition")');
    end
    %----------------
    if chos==2,
        clc;
        if exist('img')
            if (exist('iris_database.dat')==2)
                load('iris_database.dat','-mat');
                face_number=face_number+1;

                prompt={sprintf('%s',messaggio,'Class number must be a positive integer <= ',num2str(max_class))};
                title='Class number';
                lines=1;
                def={'1'};
                answer=inputdlg(prompt,title,lines,def);
                zparameter=double(str2num(char(answer)));
                if size(zparameter,1)~=0
                    class_number=zparameter(1);
                    if (class_number<=0)||(class_number>max_class)||(floor(class_number)~=class_number)||(~isa(class_number,'double'))||(any(any(imag(class_number))))
                        warndlg(sprintf('%s','Class number must be a positive integer <= ',num2str(max_class)),' Warning ')
                    else
                        disp('Features extraction... please wait.');
                        if class_number==max_class;
                            % this person (class) has never been added to
                            % database before this moment
                            max_class = class_number+1;
                            features  = findfeatures(img)
                        else
                            % this person (class) has already been added to
                            % database
                            features  = findfeatures(img)
                        end


                        features_data{features_size+1,1} = features
                        features_data{features_size+1,2} = class_number;
                        features_data{features_size+1,3} = strcat(pathname,namefile);
                        features_size                    = size(features_data,1);
                        clc;
                        save('iris_database.dat','face_number','max_class','features_data','features_size','-append');
                        msgbox(sprintf('%s','Database already exists: image succesfully added to class number ',num2str(class_number)),'Database result','help');
                        close all;
                        clear('img');
                        clc;
                        disp('Iris image added to database.');
                        messaggio2 = sprintf('%s','Location: ',strcat(pathname,namefile));
                        disp(messaggio2);
                        messaggio2 = sprintf('%s','Iris ID: ',num2str(class_number));
                        disp(messaggio2);
                    end
                else
                    warndlg(sprintf('%s','Class number must be a positive integer <= ',num2str(max_class)),' Warning ')
                end
            else
                face_number=1;
                max_class=1;
                prompt={sprintf('%s',messaggio,'Class number must be a positive integer <= ',num2str(max_class))};
                title='Class number';
                lines=1;
                def={'1'};
                answer=inputdlg(prompt,title,lines,def);
                zparameter=double(str2num(char(answer)));
                if size(zparameter,1)~=0
                    class_number=zparameter(1);
                    if (class_number<=0)||(class_number>max_class)||(floor(class_number)~=class_number)||(~isa(class_number,'double'))||(any(any(imag(class_number))))
                        warndlg(sprintf('%s','Class number must be a positive integer <= ',num2str(max_class)),' Warning ')
                    else
                        disp('Features extraction... please wait.');
                        max_class=2;
                        features  = findfeatures(img);
                        disp('Completed.');

                        features_data{1,1} = features;
                        features_data{1,2} = class_number;
                        features_data{1,3} = strcat(pathname,namefile);
                        features_size = size(features_data,1);
                        save('iris_database.dat','face_number','max_class','features_data','features_size');
                        msgbox(sprintf('%s','Database was empty. Database has just been created. Image succesfully added to class number ',num2str(class_number)),'Database result','help');
                        clc;
                        close all;
                        clear('img');
                        disp('Iris image added to database.');
                        messaggio2 = sprintf('%s','Location: ',strcat(pathname,namefile));
                        disp(messaggio2);
                        messaggio2 = sprintf('%s','Iris ID: ',num2str(class_number));
                        disp(messaggio2);
                    end
                else
                    warndlg(sprintf('%s','Class number must be a positive integer <= ',num2str(max_class)),' Warning ')
                end

            end
        else
            errordlg('No image has been selected.','File Error');
        end
    end
    %----------------
    if chos==3,
        clc;
        close all;
        clear('img');
        if (exist('iris_database.dat')==2)
            load('iris_database.dat','-mat');
            msgbox(sprintf('%s','Database has ',num2str(face_number),' image(s). There are',num2str(max_class-1),' class(es). Input images must have the same size.'),'Database result','help');
            disp('Iris images present in database:');
            disp('---');
            for ii=1:features_size
                messaggio2 = sprintf('%s','Location: ',features_data{ii,3});%,' ID: ',num2str(features_data{ii,2}));
                disp(messaggio2);
                messaggio2 = sprintf('%s','Iris ID: ',num2str(features_data{ii,2}));
                disp(messaggio2);
                disp('---');
            end
        else
            msgbox('Database is empty.','Database result','help');
        end
    end
    %----------------
    if chos==4,
        clc;
        close all;
        if exist('img')

            if (exist('iris_database.dat')==2)
                load('iris_database.dat','-mat');
                disp('Features extraction for iris recognition... please wait.');
                % code for iris recognition
                features  = findfeatures(img);

                messaggio2 = sprintf('%s','Input iris image: ',strcat(pathname,namefile));
                disp(messaggio2);
                disp('---');
                pesi = zeros(features_size,1);
                for ii=1:features_size
                    messaggio2 = sprintf('%s','Current scanned iris image:',features_data{ii,3},' ID: ',num2str(features_data{ii,2}));
                    disp(messaggio2);
                    % hd = gethammingdistance(template1, mask1, template2,mask2, scales)
                    template1 = features{1};
                    mask1     = features{2};
                    template2 = features_data{ii,1}{1};
                    mask2     = features_data{ii,1}{2};
                    pesi(ii)  = gethammingdistance(template1, mask1, template2,mask2);
                end
                [val,pos] = min(pesi);
                pos       = features_data{pos,2};
                disp('---');
                messaggio2 = sprintf('%s','Recognized iris image: ',features_data{pos,3});
                disp(messaggio2);
                disp('Recognized ID');
                disp(pos);
            else
                warndlg('No image processing is possible. Database is empty.',' Warning ')
            end
        else
            warndlg('Input image must be selected.',' Warning ')
        end
    end
    %----------------
    if chos==5,
        clc;
        close all;
        disp('GA training is going to start.');
        disp('If you manually stop training with CTR+C please rename gabest.mat as gabest_opt.mat');
        pause(2);
        % options(1) = m (size of generation, must be even integer)
        % options(2) = eta (crossover rate in (0,1) or use Booker's VCO if < 0)
        % options(3) = gamma (mutation rate in (0,1))
        % options(4) = printcnt (print status once every printcnt iterations)
        %                Set printcnt to zero to suppress printout.
        % options(5) = maxiter (maximum number of iterations)
        % options(6) = stopiter (minimum number of gains < epsln before stop)
        % options(7) = epsln (smallest gain worth recognizing)
        % options(8) = rplcbest (every rplcbest iterations, insert best-so-far)
        % options(9) = 1 if function is vectorized (i.e., if the function
        %                can simultaneously evaluate many parameter vectors).
        options         = [20,-1,0.12,10,20000,2000,1e-4,50,0];
        minbound        = -50*ones(1,10);
        maxbound        = +50*ones(1,10);
        parspace        = [minbound; maxbound];
        [beta,stopcode] = ga('recognitionrate',parspace,options);
        save gabest_opt beta;
        disp('Optimized filter:');
        disp(beta);
    end
    %----------------
    if chos==6,
        clc;
        close all;
        if (exist('iris_database.dat')==2)
            button = questdlg('Do you really want to remove the Database?');
            if strcmp(button,'Yes')
                delete('iris_database.dat');
                clear('face_number','max_class','features_data','features_size');
                msgbox('Database was succesfully removed from the current directory.','Database removed','help');
            end
        else
            warndlg('Database is empty.',' Warning ')
        end
    end
    %----------------
    if chos==7,
        clc;
        close all;
        helpwin readme;
    end
    %----------------
    if chos==8,
        clc;
        close all;
        web http://www.advancedsourcecode.com/gairis.asp
        helpwin sourcecode;
    end
end
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function newim = adjgamma(im, g)

if g <= 0
    error('Gamma value must be > 0');
end

if isa(im,'uint8');
    newim = double(im);
else
    newim = im;
end

% rescale range 0-1
newim = newim-min(min(newim));
newim = newim./max(max(newim));

newim =  newim.^(1/g);   % Apply gamma function
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function [gradient, or] = canny(im, sigma, scaling, vert, horz)

xscaling = vert;
yscaling = horz;

hsize = [6*sigma+1, 6*sigma+1];   % The filter size.

gaussian = fspecial('gaussian',hsize,sigma);
im = filter2(gaussian,im);        % Smoothed image.

im = imresize(im, scaling);

[rows, cols] = size(im);

h =  [  im(:,2:cols)  zeros(rows,1) ] - [  zeros(rows,1)  im(:,1:cols-1)  ];
v =  [  im(2:rows,:); zeros(1,cols) ] - [  zeros(1,cols); im(1:rows-1,:)  ];
d1 = [  im(2:rows,2:cols) zeros(rows-1,1); zeros(1,cols) ] - ...
    [ zeros(1,cols); zeros(rows-1,1) im(1:rows-1,1:cols-1)  ];
d2 = [  zeros(1,cols); im(1:rows-1,2:cols) zeros(rows-1,1);  ] - ...
    [ zeros(rows-1,1) im(2:rows,1:cols-1); zeros(1,cols)   ];

X = ( h + (d1 + d2)/2.0 ) * xscaling;
Y = ( v + (d1 - d2)/2.0 ) * yscaling;

gradient = sqrt(X.*X + Y.*Y); % Gradient amplitude.

or = atan2(-Y, X);            % Angles -pi to + pi.
neg = or<0;                   % Map angles to 0-pi.
or = or.*~neg + (or+pi).*neg;
or = or*180/pi;               % Convert to degrees.
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
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
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
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
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function lines = findline(image)

[I2 or] = canny(image, 2, 1, 0.00, 1.00);

I3 = adjgamma(I2, 1.9);
I4 = nonmaxsup(I3, or, 1.5);
edgeimage = hysthresh(I4, 0.20, 0.15);


theta = (0:179)';
[R, xp] = radon(edgeimage, theta);

maxv = max(max(R));

if maxv > 25
    i = find(R == max(max(R)));
else
    lines = [];
    return;
end

[foo, ind] = sort(-R(i));
u = size(i,1);
k = i(ind(1:u));
[y,x]=ind2sub(size(R),k);
t = -theta(x)*pi/180;
r = xp(y);

lines = [cos(t) sin(t) -r];

cx = size(image,2)/2-1;
cy = size(image,1)/2-1;
lines(:,3) = lines(:,3) - lines(:,1)*cx - lines(:,2)*cy;
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function h = houghcircle(edgeim, rmin, rmax)
%% This function is replaced by the faster mex implementation
%% Original m-code:
% [rows,cols] = size(edgeim);
% nradii = rmax-rmin+1;
% h = zeros(rows,cols,nradii);
%
%
% [y,x] = find(edgeim~=0);
%
% hr     = rows;
% hc     = cols;
% hrhc   = hr*hc;
% weight = 1;
%
% %for each edge point, draw circles of different radii
% for index=1:size(y)
%
%     cx = x(index);
%     cy = y(index);
%
%     c  = [cx,cy];
%
%     for n=1:nradii
%
%
%         radius   = n+rmin;
%         xd       = 0:fix(radius/sqrt(2));
%         costheta = sqrt(1-(xd.^2/radius^2));
%         yd       = round(radius*costheta);
%         px       = c(2) + [xd  yd  yd  xd -xd -yd -yd -xd];
%         py       = c(1) + [yd  xd -xd -yd -yd -xd  xd  yd];
%         validx   = px>=1 & px<=hr;
%         validy   = py>=1 & py<=hc;
%         valid    = find(validx & validy);
%         px       = px(valid);
%         py       = py(valid);
%         ind      = px+(py-1)*hr;
%
%         positions    = ind+(n-1)*hrhc;
%         h(positions) = h(positions)+ weight;
%         % h(ind+(n-1)*hr*hc) = h(ind+(n-1)*hr*hc)+ weight;
%     end
%
% end
%% Mex implementation:
h = mexhcimpl(edgeim,rmin,rmax);
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function bw = hysthresh(im, T1, T2)

if (T2 > T1 | T2 < 0 | T1 < 0)  % Check thesholds are sensible
    error('T1 must be >= T2 and both must be >= 0 ');
end

[rows, cols] = size(im);    % Precompute some values for speed and convenience.
rc = rows*cols;
rcmr = rc - rows;
rp1 = rows+1;

bw = im(:);                 % Make image into a column vector
pix = find(bw > T1);        % Find indices of all pixels with value > T1
npix = size(pix,1);         % Find the number of pixels with value > T1

stack = zeros(rows*cols,1); % Create a stack array (that should never
% overflow!)

stack(1:npix) = pix;        % Put all the edge points on the stack
stp = npix;                 % set stack pointer
for k = 1:npix
    bw(pix(k)) = -1;        % mark points as edges
end


% Precompute an array, O, of index offset values that correspond to the eight
% surrounding pixels of any point. Note that the image was transformed into
% a column vector, so if we reshape the image back to a square the indices
% surrounding a pixel with index, n, will be:
%              n-rows-1   n-1   n+rows-1
%
%               n-rows     n     n+rows
%
%              n-rows+1   n+1   n+rows+1

O = [-1, 1, -rows-1, -rows, -rows+1, rows-1, rows, rows+1];

while stp ~= 0            % While the stack is not empty
    v = stack(stp);         % Pop next index off the stack
    stp = stp - 1;

    if v > rp1 & v < rcmr   % Prevent us from generating illegal indices
        % Now look at surrounding pixels to see if they
        % should be pushed onto the stack to be
        % processed as well.
        index = O+v;	    % Calculate indices of points around this pixel.
        for l = 1:8
            ind = index(l);
            if bw(ind) > T2   % if value > T2,
                stp = stp+1;  % push index onto the stack.
                stack(stp) = ind;
                bw(ind) = -1; % mark this as an edge point
            end
        end
    end
end



bw = (bw == -1);            % Finally zero out anything that was not an edge
bw = reshape(bw,rows,cols); % and reshape the image
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function [x,y] = linecoords(lines, imsize)

xd = [1:imsize(2)];
yd = (-lines(3) - lines(1)*xd ) / lines(2);

coords = find(yd>imsize(1));
yd(coords) = imsize(1);
coords = find(yd<1);
yd(coords) = 1;

x = int32(xd);
y = int32(yd);
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function im = nonmaxsup(inimage, orient, radius)

if size(inimage) ~= size(orient)
    error('image and orientation image are of different sizes');
end

if radius < 1
    error('radius must be >= 1');
end

[rows,cols] = size(inimage);
im = zeros(rows,cols);        % Preallocate memory for output image for speed
iradius = ceil(radius);

% Precalculate x and y offsets relative to centre pixel for each orientation angle

angle = [0:180].*pi/180;    % Array of angles in 1 degree increments (but in radians).
xoff = radius*cos(angle);   % x and y offset of points at specified radius and angle
yoff = radius*sin(angle);   % from each reference position.

hfrac = xoff - floor(xoff); % Fractional offset of xoff relative to integer location
vfrac = yoff - floor(yoff); % Fractional offset of yoff relative to integer location

orient = fix(orient)+1;     % Orientations start at 0 degrees but arrays start
% with index 1.

% Now run through the image interpolating grey values on each side
% of the centre pixel to be used for the non-maximal suppression.

for row = (iradius+1):(rows - iradius)
    for col = (iradius+1):(cols - iradius)

        or = orient(row,col);   % Index into precomputed arrays

        x = col + xoff(or);     % x, y location on one side of the point in question
        y = row - yoff(or);

        fx = floor(x);          % Get integer pixel locations that surround location x,y
        cx = ceil(x);
        fy = floor(y);
        cy = ceil(y);
        tl = inimage(fy,fx);    % Value at top left integer pixel location.
        tr = inimage(fy,cx);    % top right
        bl = inimage(cy,fx);    % bottom left
        br = inimage(cy,cx);    % bottom right

        upperavg = tl + hfrac(or) * (tr - tl);  % Now use bilinear interpolation to
        loweravg = bl + hfrac(or) * (br - bl);  % estimate value at x,y
        v1 = upperavg + vfrac(or) * (loweravg - upperavg);

        if inimage(row, col) > v1 % We need to check the value on the other side...

            x = col - xoff(or);     % x, y location on the `other side' of the point in question
            y = row + yoff(or);

            fx = floor(x);
            cx = ceil(x);
            fy = floor(y);
            cy = ceil(y);
            tl = inimage(fy,fx);    % Value at top left integer pixel location.
            tr = inimage(fy,cx);    % top right
            bl = inimage(cy,fx);    % bottom left
            br = inimage(cy,cx);    % bottom right
            upperavg = tl + hfrac(or) * (tr - tl);
            loweravg = bl + hfrac(or) * (br - bl);
            v2 = upperavg + vfrac(or) * (loweravg - upperavg);

            if inimage(row,col) > v2            % This is a local maximum.
                im(row, col) = inimage(row, col); % Record value in the output image.
            end

        end
    end
end
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function [polar_array, polar_noise] = normaliseiris(image, x_iris, y_iris, r_iris,...
    x_pupil, y_pupil, r_pupil, radpixels, angulardiv)

% global DIAGPATH

radiuspixels = radpixels + 2;
angledivisions = angulardiv-1;

r = 0:(radiuspixels-1);

theta = 0:2*pi/angledivisions:2*pi;

x_iris = double(x_iris);
y_iris = double(y_iris);
r_iris = double(r_iris);

x_pupil = double(x_pupil);
y_pupil = double(y_pupil);
r_pupil = double(r_pupil);

% calculate displacement of pupil center from the iris center
ox = x_pupil - x_iris;
oy = y_pupil - y_iris;

if ox <= 0
    sgn = -1;
elseif ox > 0
    sgn = 1;
end

if ox==0 && oy > 0

    sgn = 1;

end

r = double(r);
theta = double(theta);

a = ones(1,angledivisions+1)* (ox^2 + oy^2);

% need to do something for ox = 0
if ox == 0
    phi = pi/2;
else
    phi = atan(oy/ox);
end

b = sgn.*cos(pi - phi - theta);

% calculate radius around the iris as a function of the angle
r = (sqrt(a).*b) + ( sqrt( a.*(b.^2) - (a - (r_iris^2))));

r = r - r_pupil;

rmat = ones(1,radiuspixels)'*r;

rmat = rmat.* (ones(angledivisions+1,1)*[0:1/(radiuspixels-1):1])';
rmat = rmat + r_pupil;


% exclude values at the boundary of the pupil iris border, and the iris scelra border
% as these may not correspond to areas in the iris region and will introduce noise.
%
% ie don't take the outside rings as iris data.
rmat  = rmat(2:(radiuspixels-1), :);

% calculate cartesian location of each data point around the circular iris
% region
xcosmat = ones(radiuspixels-2,1)*cos(theta);
xsinmat = ones(radiuspixels-2,1)*sin(theta);

xo = rmat.*xcosmat;
yo = rmat.*xsinmat;

xo = x_pupil+xo;
yo = y_pupil-yo;

% extract intensity values into the normalised polar representation through
% interpolation
[x,y] = meshgrid(1:size(image,2),1:size(image,1));
polar_array = interp2(x,y,image,xo,yo);

% create noise array with location of NaNs in polar_array
polar_noise = zeros(size(polar_array));
coords = find(isnan(polar_array));
polar_noise(coords) = 1;

polar_array = double(polar_array)./255;


% start diagnostics, writing out eye image with rings overlayed

% get rid of outling points in order to write out the circular pattern
coords = find(xo > size(image,2));
xo(coords) = size(image,2);
coords = find(xo < 1);
xo(coords) = 1;

coords = find(yo > size(image,1));
yo(coords) = size(image,1);
coords = find(yo<1);
yo(coords) = 1;

xo = round(xo);
yo = round(yo);

xo = int32(xo);
yo = int32(yo);

%ind1 = sub2ind(size(image),double(yo),double(xo));

%image = uint8(image);

%image(ind1) = 255;
%get pixel coords for circle around iris
%[x,y] = circlecoords([x_iris,y_iris],r_iris,size(image));
%ind2 = sub2ind(size(image),double(y),double(x));
%get pixel coords for circle around pupil
%[xp,yp] = circlecoords([x_pupil,y_pupil],r_pupil,size(image));
%ind1 = sub2ind(size(image),double(yp),double(xp));

%image(ind2) = 255;
%image(ind1) = 255;


% write out rings overlaying original iris image
% w = cd;
% cd(DIAGPATH);
%
% imwrite(image,[eyeimage_filename,'-normal.jpg'],'jpg');
%
% cd(w);

% end diagnostics

%replace NaNs before performing feature encoding
coords = find(isnan(polar_array));
polar_array2 = polar_array;
polar_array2(coords) = 0.5;
avg = sum(sum(polar_array2)) / (size(polar_array,1)*size(polar_array,2));
polar_array(coords) = avg;
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function [circleiris, circlepupil, imagewithnoise] = segmentiris(eyeimage)

% define range of pupil & iris radii

%CASIA
lpupilradius = 28;
upupilradius = 75;
lirisradius = 80;
uirisradius = 150;

%    %LIONS
%    lpupilradius = 32;
%    upupilradius = 85;
%    lirisradius = 145;
%    uirisradius = 169;


% define scaling factor to speed up Hough transform
scaling = 0.4;

reflecthres = 240;

% find the iris boundary
[row, col, r] = findcircle(eyeimage, lirisradius, uirisradius, scaling, 2, 0.20, 0.19, 1.00, 0.00);

circleiris = [row col r];

rowd = double(row);
cold = double(col);
rd = double(r);

irl = round(rowd-rd);
iru = round(rowd+rd);
icl = round(cold-rd);
icu = round(cold+rd);

imgsize = size(eyeimage);

if irl < 1
    irl = 1;
end

if icl < 1
    icl = 1;
end

if iru > imgsize(1)
    iru = imgsize(1);
end

if icu > imgsize(2)
    icu = imgsize(2);
end

% to find the inner pupil, use just the region within the previously
% detected iris boundary
imagepupil = eyeimage( irl:iru,icl:icu);

%find pupil boundary
[rowp, colp, r] = findcircle(imagepupil, lpupilradius, upupilradius ,0.6,2,0.25,0.25,1.00,1.00);

rowp = double(rowp);
colp = double(colp);
r = double(r);

row = double(irl) + rowp;
col = double(icl) + colp;

row = round(row);
col = round(col);

circlepupil = [row col r];

% set up array for recording noise regions
% noise pixels will have NaN values
imagewithnoise = double(eyeimage);

%find top eyelid
topeyelid = imagepupil(1:(rowp-r),:);
lines = findline(topeyelid);

if size(lines,1) > 0
    [xl yl] = linecoords(lines, size(topeyelid));
    yl = double(yl) + irl-1;
    xl = double(xl) + icl-1;

    yla = max(yl);

    y2 = 1:yla;

    ind3 = sub2ind(size(eyeimage),yl,xl);
    imagewithnoise(ind3) = NaN;

    imagewithnoise(y2, xl) = NaN;
end

%find bottom eyelid
bottomeyelid = imagepupil((rowp+r):size(imagepupil,1),:);
lines = findline(bottomeyelid);

if size(lines,1) > 0

    [xl yl] = linecoords(lines, size(bottomeyelid));
    yl = double(yl)+ irl+rowp+r-2;
    xl = double(xl) + icl-1;

    yla = min(yl);

    y2 = yla:size(eyeimage,1);

    ind4 = sub2ind(size(eyeimage),yl,xl);
    imagewithnoise(ind4) = NaN;
    imagewithnoise(y2, xl) = NaN;

end

%For CASIA, eliminate eyelashes by thresholding
ref = eyeimage < 100;
coords = find(ref==1);
imagewithnoise(coords) = NaN;
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function templatenew = shiftbits(template, noshifts,nscales)

templatenew = zeros(size(template));

width = size(template,2);
s = round(2*nscales*abs(noshifts));
p = round(width-s);

if noshifts == 0
    templatenew = template;

    % if noshifts is negatite then shift towards the left
elseif noshifts < 0

    x=1:p;

    templatenew(:,x) = template(:,s+x);

    x=(p + 1):width;

    templatenew(:,x) = template(:,x-p);

else

    x=(s+1):width;

    templatenew(:,x) = template(:,x-s);

    x=1:s;

    templatenew(:,x) = template(:,p+x);

end
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function [out]=findfeatures(eyeimage)
radial_res  = 20;
angular_res = 240;

% with these settings a 9600 bit iris template is
% created

%feature encoding parameters
%nscales=scales;
%minWaveLength=18;
%mult=1; % not applicable if using nscales = 1
%sigmaOnf=0.5;

[circleiris circlepupil imagewithnoise] = segmentiris(eyeimage);


%imagewithnoise2 = uint8(imagewithnoise);
%imagewithcircles = uint8(eyeimage);

%get pixel coords for circle around iris
%[x,y] = circlecoords([circleiris(2),circleiris(1)],circleiris(3),size(eyeimage));
%ind2 = sub2ind(size(eyeimage),double(y),double(x));

%get pixel coords for circle around pupil
%[xp,yp] = circlecoords([circlepupil(2),circlepupil(1)],circlepupil(3),size(eyeimage));
%ind1 = sub2ind(size(eyeimage),double(yp),double(xp));


%imagewithnoise2(ind2) = 255;
%imagewithnoise2(ind1) = 255;
% Write circles overlayed
%imagewithcircles(ind2) = 255;
%imagewithcircles(ind1) = 255;

[polar_array noise_array] = normaliseiris(imagewithnoise, circleiris(2),...
    circleiris(1), circleiris(3), circlepupil(2), circlepupil(1), circlepupil(3), radial_res, angular_res);


[template mask] = encode(polar_array, noise_array);
out{1} = template;
out{2} = mask;
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function hd = gethammingdistance(template1, mask1, template2, mask2)

m0 = mask1;
m1 = mask2;

f0 = template1;
f1 = template2;

punteggiomax = -Inf;

for myshift = -4:1:4
    m1shift =  circshift(m1,[0 myshift]);
    f1shift =  circshift(f1,[0 myshift]);

    m0m1      = not(m0) & not(m1shift);
    uguali    = not(xor(f0,f1shift)) & m0m1;

    punteggio = sum(uguali(:)) / sum(m0m1(:));
    if punteggiomax<punteggio
        punteggiomax = punteggio;
    end
end
punteggio = punteggiomax;

%m0m1      = not(m0) & not(m1);
%uguali    = not(xor(f0,f1)) & m0m1;
%punteggio = sum(uguali(:)) / sum(m0m1(:));

hd = 1-punteggio;
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function [score]=recognitionrate(x)
x = x(:)';
% load iris db
load irisdb_file;
% example:
% irisdatabase(78,7).iris
% irisdatabase(78,7).mask
% resets the generator to its initial state
rand('state',0);
% Size irisdatabase: 108 x 7
N = size(irisdatabase,1);
M = size(irisdatabase,2);

% Avg filter
avgfilter = ones(size(x));
avgfilter = avgfilter/sum(avgfilter);
disp('Filtering in progress...');
% Filtered iris
for ii=1:N
    for jj=1:M
        img               = irisdatabase(ii,jj).iris;
        f1                = conv2(img,avgfilter,'same');
        f2                = conv2(f1-img,x,'same');
        filterDB(ii,jj).f = f2>0;
    end
end
disp('Filtering completed.');
% Recognition rate
correctmatch = 0;  % number of correct matches
totalmatch   = 0;  % number of total matches
Ntest        = 5;  % Number of test
numero       = 4;  % 4 iris images for training and the remaining ones for testing
for scantest=1:Ntest
    tic;
    posizioni = randperm(M);
    for ii0=1:N
        for jj0=1:numero
            pos0 = posizioni(jj0);
            f0 = filterDB(ii0,pos0).f;
            m0 = irisdatabase(ii0,pos0).mask;

            punteggiomax = -Inf;

            for ii1=1:N
                for jj1=(numero+1):M
                    pos1 = posizioni(jj1);
                    f1   = filterDB(ii1,pos1).f;
                    m1   = irisdatabase(ii1,pos1).mask;

                    m0m1      = not(m0) & not(m1);
                    uguali    = not(xor(f0,f1)) & m0m1;
                    punteggio = sum(uguali(:)) / sum(m0m1(:));
                    if punteggiomax<punteggio
                        punteggiomax = punteggio;
                        idrec        = ii1;
                    end
                end
            end
            totalmatch = totalmatch+1;
            if idrec==ii0
                correctmatch = correctmatch+1;
            else
                messaggio = sprintf('%s%s','Recognition rate: ',num2str(correctmatch/totalmatch));
                disp(messaggio);
            end
            if mod(totalmatch,100)==0
                % Escape if recognition rate is too low
                score = correctmatch/totalmatch;
                if score<0.800
                    tempo = toc;
                    messaggio = sprintf('%s%s','Time required for 1:N matching: ',num2str(tempo));
                    disp(messaggio);
                    messaggio = sprintf('%s%s','Total number of matches: ',num2str(totalmatch));
                    disp(messaggio);
                    messaggio = sprintf('%s%s','Total number of errors: ',num2str(totalmatch-correctmatch));
                    disp(messaggio);
                    disp('--------');
                    disp('Escape since recognition rate is too low.');
                    disp('Final recognition rate:');
                    disp(score);
                    disp('Genetic vector:');
                    disp(x);
                    disp('--------');
                    return;
                end
            end
        end
    end
    tempo = toc;
    messaggio = sprintf('%s%s','Time required for 1:N matching: ',num2str(tempo));
    disp(messaggio);
    messaggio = sprintf('%s%s','Total number of matches: ',num2str(totalmatch));
    disp(messaggio);
    messaggio = sprintf('%s%s','Total number of errors: ',num2str(totalmatch-correctmatch));
    disp(messaggio);
end
score = correctmatch/totalmatch;
disp('--------');
disp('Final recognition rate:');
disp(score);
disp('Genetic vector:');
disp(x);
disp('--------');
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
function [beta,stopcode]=ga(funstr,parspace,options,p1,p2,p3,p4,p5,p6,p7,p8,p9)
%[beta,stopcode]=ga(funstr,parspace,options,p1,p2,p3,p4,p5,p6,p7,p8,p9)
% Genetic Algorithm for function maximization.
% Especially useful for functions with kinks and discontinuities,
% and where a good "starting point" is unavailable.
%    See Dorsey and Mayer,
%    Journal of Business and Economic Statistics, January 1995, 13(1)
% Program by Michael Gordy <m1mbg00@frb.gov>
% Version 1.13, 12 February 1996, written for Matlab v4.2c
%
% OUTPUTS:
%  beta       = (1 x K) parameter vector maximizing funstr
%  stopcode   = code for terminating condition
%                == 1 if terminated normally
%                == 2 if maximum number of iterations exceeded
%
% INPUTS:
%  funstr     = name of function to be maximized (string).
%  parspace   = (2 x K) matrix is [min; max] of parameter space dimensions
%               or, if (3 x K), then bottom row is a good starting value
%  options    = vector of option settings
%  p1,p2,...,p9 are optional parameters to be passed to funstr
%
% where:
% options(1) = m (size of generation, must be even integer)
% options(2) = eta (crossover rate in (0,1) or use Booker's VCO if < 0)
% options(3) = gamma (mutation rate in (0,1))
% options(4) = printcnt (print status once every printcnt iterations)
%                Set printcnt to zero to suppress printout.
% options(5) = maxiter (maximum number of iterations)
% options(6) = stopiter (minimum number of gains < epsln before stop)
% options(7) = epsln (smallest gain worth recognizing)
% options(8) = rplcbest (every rplcbest iterations, insert best-so-far)
% options(9) = 1 if function is vectorized (i.e., if the function
%                can simultaneously evaluate many parameter vectors).
%    Default option settings: [20,-1,0.12,10,20000,2000,1e-4,50,0]
%
% Notes:
% 1. The function is maximized with respect to its first parameter,
%    which is expressed as a row vector.
%    Example:
%      Say we want to maximize function f with respect to vector p,
%      and need also to pass to f data matrices x,y,z.  Then,
%      write the function f so it is called as f(p,x,y,z).  GA will
%      assume that p is a row vector.
% 2. Intermediate results are saved to "gabest.mat".  This allows
%    you to pick up where you left off after an interruption.

gaver='1.13';
defopt=[24,-1,0.12,10,20000,2000,1e-4,50,0];
months = ['Jan';'Feb';'Mar';'Apr';'May';'Jun';...
    'Jul';'Aug';'Sep';'Oct';'Nov';'Dec'];

if nargin>2
    if isempty(options)
        options=defopt;
    end
else
    options=defopt;
end
m=options(1); eta=options(2); gam=options(3);
printcnt=options(4);
maxiter=options(5);
stopiter=options(6); epsln=options(7);
rplcbest=options(8);
vecfun=options(9);

% Use Booker's VCO if eta==-1
vco=(eta<0);  eta=abs(eta);

% Cancel rplcbest if <=0
if rplcbest<=0, rplcbest=maxiter+1; end

K=size(parspace,2);

% Draw initial Generation
G=rand(m,K).*(parspace(2*ones(m,1),:)-parspace(ones(m,1),:))...
    +parspace(ones(m,1),:);
b0rows=size(parspace,1)-2;
if b0rows>0
    G(1:b0rows,:)=parspace(3:b0rows+2,:);
    parspace=parspace([1 2],:);
end

% Initial 'best' holders
inarow=0;
bestfun=-Inf; beta=zeros(1,K);

% Score for each of m vectors
f=zeros(m,1);

% Setup function string for evaluations
paramstr=',p1,p2,p3,p4,p5,p6,p7,p8,p9';
evalstr = [funstr,'(G'];
if ~vecfun
    evalstr=[evalstr, '(i,:)'];
end
if nargin>3, evalstr=[evalstr,paramstr(1:3*(nargin-3))]; end
evalstr = [evalstr, ')'];

% Print header
if printcnt>0
    disp(['GA (Genetic Algorithm), Version ',gaver,' by Michael Gordy'])
    disp(['Maximization of function ',funstr])
    disp('i      = Current generation')
    disp('best_i = Best function value in generation i')
    disp('best   = Best function value so far')
    disp('miss   = Number of generations since last hit')
    disp('psi    = Proportion of unique genomes in generation')
    disp(sprintf(['\n',blanks(20),'i     best_i        best     miss   psi']))
end

iter=0;  stopcode=0;
oldpsi=1;  % for VCO option
while stopcode==0
    iter=iter+1;
    % Call function for each vector in G
    if vecfun
        f=eval(evalstr);
    else
        for i=1:m
            f(i)=eval(evalstr);
        end
    end
    f0=f;
    [bf0,bx]=max(f);
    bf=max([bf0 bestfun]);
    fgain=(bf-bestfun);
    if fgain>epsln
        inarow=0;
    else
        inarow=inarow+1;
    end
    if fgain>0
        bestfun=bf;
        beta=G(bx(1),:);
    end
    if printcnt>0 & rem(iter,printcnt)==1
        % psi is number of unique rows in G divided by m.
        psi=(1+sum(diff(sort(G*rand(K,1)))~=0))/m;
        ck=clock;
        ckhr=int2str(ck(4)+100);  ckday=int2str(ck(3)+100);
        ckmin=int2str(ck(5)+100); cksec=int2str(ck(6)+100);
        timestamp=[ckday(2:3),months(ck(2),:),' ',...
            ckhr(2:3),':',ckmin(2:3),':',cksec(2:3),' '];
        disp([timestamp,sprintf('%6.0f %8.5e %8.5e %5.0f %5.3f',...
            [iter bf0 bestfun inarow psi])])
        save gabest beta timestamp iter funstr
    end
    % Reproduction
    f=(f-min(f)).^(1+log(iter)/100);
    pcum=cumsum(f)/sum(f);
    r=rand(1,m); r=sum(r(ones(m,1),:)>pcum(:,ones(1,m)))+1;
    G=G(r,:);
    % Crossover
    if vco
        % psi is number of unique rows in G divided by m.
        psi=(1+sum(diff(sort(G*rand(K,1)))~=0))/m;
        eta=max([0.2 min([1,eta-psi+oldpsi])]);
        oldpsi=psi;
    end
    y=sum(rand(m/2,1)<eta);
    if y>0
        % choose crossover point
        x=floor(rand(y,1)*(K-1))+1;
        for i=1:y
            tmp=G(i,x(i)+1:K);
            G(i,x(i)+1:K)=G(i+m/2,x(i)+1:K);
            G(i+m/2,x(i)+1:K)=tmp;
        end
    end
    % Mutation
    M=rand(m,K).*(parspace(2*ones(m,1),:)-parspace(ones(m,1),:))...
        +parspace(ones(m,1),:);
    domuta=find(rand(m,K)<gam);
    G(domuta)=M(domuta);
    % Once every rplcbest iterations, re-insert best beta
    if rem(iter,rplcbest)==0
        G(m,:)=beta;
    end
    stopcode=(inarow>stopiter)+2*(iter>maxiter);
end

if printcnt>0
    if stopcode==1
        disp(sprintf('GA: No improvement in %5.0f generations.\n',stopiter))
    else
        disp(sprintf('GA: Maximum number of iterations exceeded.\n'))
    end
end
%% end of ga.m
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------
%--------------------------------------------------------------------------




