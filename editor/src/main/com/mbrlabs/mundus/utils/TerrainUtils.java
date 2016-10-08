/*
 * Copyright (c) 2016. See AUTHORS file.
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

package com.mbrlabs.mundus.utils;

import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder.VertexInfo;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.mbrlabs.mundus.commons.assets.TerrainAsset;
import com.mbrlabs.mundus.commons.scene3d.GameObject;
import com.mbrlabs.mundus.commons.scene3d.SceneGraph;
import com.mbrlabs.mundus.commons.terrain.Terrain;
import com.mbrlabs.mundus.commons.terrain.TerrainShader;
import com.mbrlabs.mundus.scene3d.components.TerrainComponent;

/**
 * @author Marcus Brummer
 * @version 27-02-2016
 */
public class TerrainUtils {

    public static VertexInfo tempVI = new VertexInfo();

    public static GameObject createTerrainGO(SceneGraph sg, TerrainShader shader, int goID, String goName,
            TerrainAsset terrain) {
        GameObject terrainGO = new GameObject(sg, null, goID);
        terrainGO.name = goName;

        terrain.getTerrain().setTransform(terrainGO.getTransform());
        TerrainComponent terrainComponent = new TerrainComponent(terrainGO);
        terrainComponent.setTerrain(terrain);
        terrainGO.getComponents().add(terrainComponent);
        terrainComponent.setShader(shader);
        terrainComponent.encodeRaypickColorId();

        return terrainGO;
    }

    //    public static TerrainAsset createTerrain(EditorAssetManager assetManager, int width, int depth, int vertexRes) throws IOException {
    ////        TerrainAsset asset = assetManager.createTerrainAsset(180, 1200, 1200);
    ////        asset.load();
    ////        asset.applyDependencies();
    ////
    ////        Terrain terrain = new Terrain(vertexRes);
    ////        terrain.terrainWidth = width;
    ////        terrain.terrainDepth = depth;
    ////        terrain.init();
    ////        terrain.update();
    //
    //       // TerrainTexture terrainTex = terrain.getTerrainTexture();
    ////        MTexture base = new MTexture();
    ////        base.setId(-1);
    ////        base.texture = TextureUtils.loadMipmapTexture(Gdx.files.internal("textures/terrain/chess.png"), true);
    //        // terrainTex.setSplatTexture(new SplatTexture(SplatTexture.Channel.BASE, base));
    //
    //        return null;
    //    }

    public static Vector3 getRayIntersection(Array<Terrain> terrains, Ray ray, Vector3 out) {
        for (Terrain terrain : terrains) {
            terrain.getRayIntersection(out, ray);
            if (terrain.isOnTerrain(out.x, out.z)) {
                return out;
            }
        }
        return null;
    }

    public static VertexInfo getRayIntersectionAndUp(Array<Terrain> terrains, Ray ray) {
        for (Terrain terrain : terrains) {
            terrain.getRayIntersection(tempVI.position, ray);
            if (terrain.isOnTerrain(tempVI.position.x, tempVI.position.z)) {
                tempVI.normal.set(terrain.getNormalAtWordCoordinate(tempVI.position.x, tempVI.position.z));
                return tempVI;
            }
        }
        return null;
    }
}
