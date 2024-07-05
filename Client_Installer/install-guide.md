# Abstract
 This write-up guides you through the step-by-step process on how to set up omniORB for those who are having a difficult time following the documentation, mainly beginners, this is based on official documentation provided by omniORB. To compile omniORBpy and generate it's python bindings, we would also need the omniORB library.

# Pre-Requisites
### Visual Studio
Any version of Visual Studio will do; this will be needed to compile the omniORB package, alternatively, you can use MinGW to compile omniORB, although it is not that documented.
Including **"Desktop Development for C++"** saves you the hassle of fixing your PATH for C++ development so this is **highly recommended** as shown below:

![Installation Screenshot](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/486c80cc-489e-454b-995f-7988b05ef8ef)

### Cygwin
This will be used on top of the installed packages from Visual Studio, refer to [Cygwin site](https://x.cygwin.com/docs/ug/setup.html) to install cygwin. Ensure that you include "make" to your install of cygwin
shall you have problems retrieving the list of mirrors for the installer you can add [these](https://www.cygwin.com/mirrors.htm) as your mirror inside the installer using the GUI

After installation you would need to add the bin of cygwin to your PATH, the default path would typically be `C:/cygwin64/bin`.According to the official documentation of omniORB, cygwin and Python need to be at the END of your path since Cygwin has some executable that conflicts with the installation of Visual Studio. After adding this to your PATH it should look like the image below:

![image](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/f95138e0-27ce-4913-a0ef-e39cab6b0cb4)



### omniORB & omniORBpy
We would need to download omniORB and omniORBpy, this can be obtained [here](https://omniorb.sourceforge.io/)

# Steps
### Step 1. Extract the files
Create a directory under C:/ named `python-modules` and extract omniORB and omniORBpy zip here
![Directory screenshot](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/7322a679-85ba-42b0-b45b-3d07aa73d6f7)
Moving forward the directory, `C:/python-modules/omniORB-4.3.2/` would now be referenced as $TOP[[1]](#reference_1)</a>

### Step 2. Move omniORBpy
Firstly we would need to remove the version number from "omniORBpy-4.3.2" it would be renamed to "omniORBpy". Now once the version number has been stripped from the directory name move this folder into `$TOP/src/lib`

### Step 3. Install configuration
Now we need to edit the configuration of the install; under `$TOP/omniORB-4.3.2/config`, we would be editing `config.mk`. Depending on what version of Visual Studio you installed, you would need to uncomment the corresponding platform variable. Given that you have installed the 2022 version, this would correspond to VS17, weirdly enough Microsoft labels the VS version differently from the year it is released, in this case assuming that you have installed 2022/VS-17 we would uncomment `platform = x86_win32_vs_17` as shown below:

![image](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/c8c060f8-225f-43c4-8a31-ce56e4927179)

A corresponding file would then be available under `$TOP/omniORB-4.3.2/mk/platform`, in our case that would be x86_win32_vs_17. You need to edit this file, under the Python set-up section of the config file, you need to replace the python path declaration with the location of your Python executable, take note that this needs to be in a Unix style Cygwin path. For those confused, this is typically just your Windows path, as an example instead of `C:/Python/Python312/` it would now be `/cygdrive/c/Python/Python312/`. Set the PYTHON location according to your installation below is an example in my machine:

![Screenshot 2024-06-14 234337](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/aaee18fc-bafd-45a9-a22c-e2f073ceae28)


### Step 4. Compile omniORB
Assuming that you are using Visual Studio to compile omniORB and not MinGW, you would have to run "Native Tools Command Prompt for VS...", this runs `vcvarsall.bat` automatically which is essential for building omniORB, alternatively you could run the "Developer Command Prompt" then activate `vcvarsall.bat` separately, this is located at `C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build`.

Moving on using either the "Native Tools Command Prompt" or "Developer Command Prompt" enter your $TOP directory and cd into `src` directory under this directory run the `make export` command

![image](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/abf1fb71-580f-48bb-82e9-b001a2fbc84a)

![image](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/3bf2bf78-a872-4765-8ac8-42cc5953e013)

### Step 5. Compile omniORBpy

Now to compile the omniORBpy, from `$TOP/src` cd into `lib/omniORBpy` and execute the `make export` command again

### Step 6. Use the python bindings

There are different ways to use the python bindings for your project below are some of the examples.

**Using the python bindings by adding PYTHONPATH to system environment variables**
+ Add `PYTHONPATH` with value of `$TOP/lib/python;$TOP/lib/x86_win32` as show below
 ![image](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/918db904-eff2-481d-ae35-d0475bf5eaa0)

 **Using python binding by adding PYTHONPATH to virtualenv**
 + Add `@set "PYTHONPATH=%PYTHONPATH%;$TOP/lib/x86_win32;$TOP/lib/python" to the end of your activate.bat file, this is located under the Scripts folder of your virtual env
  ![image](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/dcf9d7a7-e03a-4006-85f7-f213fbe5942b)

Below I provided an example using the compiled omniORBpy:
  
![image](https://github.com/VladTemp27/omniORBpy-windows-installation/assets/85033864/3687957c-ebf1-487e-a0df-d36090241030)



