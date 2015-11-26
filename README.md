# gradle-android-9patch-plugin

[<img src="https://circleci.com/gh/dagezi/gradle-android-9patch-plugin/tree/master.svg?style=shield&circle-token=551b03450b0b31a10d964fc38f693d3dc06f9f77">](https://circleci.com/gh/dagezi/gradle-android-9patch-plugin/tree/master)

## What's this?
With this plugin, you can programatically create 9patch PNG files from normal PNG files, Instead of manually edit PNG file to create 9patch!

## How to use this?

TODO: upload to bintray.

You can specify strech and padding information of 9patch in gradle files.

### example
```
    round_rect {
        vStretch 8, 32  // row 8 to 31 will stretch. Note that end exclusive.
        hStretch 8, -8  // Negative value specifies the distance from end
        vPadding 7, 33  // Bad example. Use even number to avoid error for 'hdpi'
        hPadding 7, -7
    }
```

See [ninepatch.gradle](sample/ninepatch.gradle) for more.

## Acknowledge
- [gradle-android-ribbonizer-plugin](https://github.com/gfx/gradle-android-ribbonizer-plugin), which inspired me and provided a lot of code to refer. 

## Copyright
```
Copyright (c) 2015 SASAKI Takesi <dagezi@gmail.com>.
```


