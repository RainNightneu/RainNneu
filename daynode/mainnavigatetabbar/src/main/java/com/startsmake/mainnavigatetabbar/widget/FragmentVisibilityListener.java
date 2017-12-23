/*
 * Copyright (C) 2016 Serhiy Brukhanda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.startsmake.mainnavigatetabbar.widget;

/**
 * The interface for receiving visibility changes for {@code Fragment}s inside {@code SuperViewPager}.
 *
 * @author Serhiy Brukhanda <http://lnkd.in/dMuBjh8>
 */
public interface FragmentVisibilityListener
{
    /**
     * Called when {@code Fragment} is visible to the user.
     */
    void onFragmentVisible();

    /**
     * Called when {@code Fragment} is invisible to the user.
     */
    void onFragmentInvisible();
}