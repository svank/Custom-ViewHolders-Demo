# Custom-ViewHolders-Demo

This demos the changes made to [`StfalconImageViewer`](https://github.com/stfalcon-studio/StfalconImageViewer) made in [this fork](https://github.com/svank/StfalconImageViewer).

![alt tag](readme_images/videos_and_text.gif)

This app uses a custom `ViewHolder` to support videos as well as images and to overlay text on the scrolling `ViewPager` pages.

The [`CustomViewHolder`](app/src/main/java/net/samvankooten/customviewholderdemo/CustomViewHolder.java) ensures there is an `ImageView` and a `VideoView` for every page, and chooses at binding time which `View` to show. Both of these views are in a `FrameLayout` along with a `TextView`, which provides the text overlay.

A [custom `ViewHolderLoader`](app/src/main/java/net/samvankooten/customviewholderdemo/MainActivity.java#L49) (just a method reference, `CustomViewHolder::buildViewHolder`) is passed to the modified `StfalconImageViewer` to ensure `CustomViewHolder` is used.
