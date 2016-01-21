/*
 * Copyright (c) 2016. See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mbrlabs.mundus.ui.components.inspector;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.mbrlabs.mundus.core.Mundus;
import com.mbrlabs.mundus.events.GameObjectSelectedEvent;
import com.mbrlabs.mundus.scene3d.GameObject;

/**
 * @author Marcus Brummer
 * @version 19-01-2016
 */
public class Inspector extends VisTable implements GameObjectSelectedEvent.GameObjectSelectedListener {

    private VisTable root;
    private ScrollPane scrollPane;

    private IdentifierWidget identifierWidget;
    private TransformWidget transformWidget;
    private Array<BaseInspectorWidget> componentWidgets;

    private VisTextButton addComponentBtn;

    public Inspector() {
        super();
        Mundus.registerEventListener(this);
        identifierWidget = new IdentifierWidget();
        transformWidget = new TransformWidget();
        componentWidgets = new Array<>();
        addComponentBtn = new VisTextButton("Add Component");

        init();
        setupUi();
        setupListeners();
    }

    public void init() {
        setBackground("default-pane");
        root = new VisTable();
        scrollPane = new ScrollPane(root);
        add(scrollPane).expand().fill();
    }

    public void setupUi() {
        root.add(new VisLabel("Inspector")).expandX().fillX().padLeft(5).row();
        root.addSeparator().row();
        root.add(identifierWidget).expand().fillX().row();
        root.add(transformWidget).expand().fillX().row();
        for(BaseInspectorWidget cw : componentWidgets) {
            root.add(cw).row();
        }
        root.add(addComponentBtn).expandX().fillX().pad(10).row();
    }

    public void setupListeners() {

    }

    @Override
    public void onGameObjectSelected(GameObjectSelectedEvent gameObjectSelectedEvent) {
        setValues(gameObjectSelectedEvent.getGameObject());
    }

    private void setValues(GameObject go) {
        identifierWidget.setValues(go);
        transformWidget.setValues(go);
    }

}
